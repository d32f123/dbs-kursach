package dbs.kursach.rest.controllers.neo4j;

import com.mongodb.Mongo;
import dbs.kursach.rest.models.neo4j.BooleanRule;
import dbs.kursach.rest.models.neo4j.CompoundRule;
import dbs.kursach.rest.models.neo4j.Condition;
import dbs.kursach.rest.models.neo4j.MongoRule;
import dbs.kursach.rest.repositories.neo4j.BooleanRuleRepository;
import dbs.kursach.rest.repositories.neo4j.CompoundRuleRepository;
import dbs.kursach.rest.repositories.neo4j.ConditionRepository;
import dbs.kursach.rest.repositories.neo4j.MongoRuleRepository;
import org.neo4j.driver.v1.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/compound-rules")
public class CompoundRuleController {

    private final CompoundRuleRepository compoundRuleRepository;
    private final ConditionRepository conditionRepository;
    private final MongoRuleRepository mongoRuleRepository;
    private final BooleanRuleRepository booleanRuleRepository;

    @Autowired
    CompoundRuleController(
            ConditionRepository conditionRepository,
            CompoundRuleRepository compoundRuleRepository,
            MongoRuleRepository mongoRuleRepository,
            BooleanRuleRepository booleanRuleRepository) {
        this.conditionRepository = conditionRepository;
        this.compoundRuleRepository = compoundRuleRepository;
        this.mongoRuleRepository = mongoRuleRepository;
        this.booleanRuleRepository = booleanRuleRepository;
    }

    private Condition saveCondition(Condition cond) {
        if (cond instanceof CompoundRule) {
            return this.compoundRuleRepository.save((CompoundRule)cond);
        } else if (cond instanceof MongoRule) {
            return this.mongoRuleRepository.save((MongoRule)cond);
        } else if (cond instanceof BooleanRule) {
            return this.booleanRuleRepository.save((BooleanRule)cond);
        } else {
            return this.conditionRepository.save(cond);
        }
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    ResponseEntity<Condition> readConditionById(@PathVariable("id") UUID ruleId) {
        Optional<Condition> rule = this.conditionRepository.findById(ruleId.toString());
        return rule.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/id/{firstId}/{secondId}", method = RequestMethod.PUT)
    ResponseEntity<?> appendExistingCondition(@PathVariable UUID firstId, @PathVariable UUID secondId) {
        try {
            Condition firstEntity = this.conditionRepository.findById(firstId.toString()).orElse(null);
            // just for testing
            firstEntity.appendSubCondition(new Condition());
            return this.conditionRepository.linkRules(firstId.toString(), secondId.toString()).map(x -> ResponseEntity.ok().build())
                    .orElse(ResponseEntity.badRequest().body("Failed to create connection. Connection already exists?"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @RequestMapping(value = "/id/{firstId}/{secondId}", method = RequestMethod.DELETE)
    ResponseEntity<?> detachConditions(@PathVariable UUID firstId, @PathVariable UUID secondId) {
        try {
            return this.conditionRepository.unlinkRukes(firstId.toString(), secondId.toString()).map(x -> ResponseEntity.ok().build())
                    .orElse(ResponseEntity.badRequest().body("Failed to detach nodes. Too far away?"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> removeCondition(@PathVariable UUID id) {
        try {
            return this.conditionRepository.removeRule(id.toString()).map(x -> ResponseEntity.ok().build())
                    .orElse(ResponseEntity.badRequest().body("Failed to remove cond. Doesn't exist?"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.POST)
    ResponseEntity<?> appendCondition(@PathVariable("id") UUID ruleId, @RequestBody ConditionRequest subConditionTemp) {
        Condition subCondition = subConditionTemp.transform(false);
        String validationRet = subCondition.validate();
        if (validationRet != null && !validationRet.isEmpty())
            return ResponseEntity.unprocessableEntity().body(validationRet);

        Optional<Condition> cond = this.conditionRepository.findById(ruleId.toString());
        return cond.map(x -> {
            try {
                x.appendSubCondition(subCondition);
                this.saveCondition(x);
                return ResponseEntity.ok(x);
            } catch (Exception ex) {
                return ResponseEntity.badRequest().body(ex.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> updateCondition(@PathVariable("id") UUID ruleId, @RequestBody ConditionRequest ruleTemp) {
        Condition rule = ruleTemp.transform(false);
        String validationRet = rule.validate();
        if (validationRet != null && !validationRet.isEmpty())
            return ResponseEntity.unprocessableEntity().body(validationRet);
        try {
            if (!this.conditionRepository.existsById(ruleId.toString()))
                return ResponseEntity.notFound().build();
            rule.setUuid(ruleId);
            saveCondition(rule);
            return ResponseEntity.ok(rule);
        } catch (ClientException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> saveCondition(@RequestBody ConditionRequest ruleTemp) {
        Condition rule = ruleTemp.transform(false);
        String validationRet = rule.validate();
        if (validationRet != null && !validationRet.isEmpty())
            return ResponseEntity.unprocessableEntity().body(validationRet);
        try {
            this.conditionRepository.save(rule);
            return ResponseEntity.ok(rule);
        } catch (ClientException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @RequestMapping(value = "/{title}", method = RequestMethod.GET)
    ResponseEntity<CompoundRule> readCompoundRuleByTitle(@PathVariable("title") String title) {
        Optional<CompoundRule> rule = this.compoundRuleRepository.findByTitle(title);
        return rule.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<CompoundRule> readAllCompoundRules() {
        List<CompoundRule> list = new ArrayList<>();
        Iterable<CompoundRule> iterable = this.compoundRuleRepository.findAll();
        iterable.forEach(list::add);
        return list;
    }
}
