package dbs.kursach.rest.controllers.mongo;

import dbs.kursach.rest.models.mongo.Rule;
import dbs.kursach.rest.repositories.mongo.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Yana Tokareva on 13.04.2018.
 */
@RestController
@RequestMapping("/rules")
public class RuleController {
    @Autowired
    RuleRepository ruleRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<Rule> get(@PathVariable("id") String id) {
        Optional<Rule> rule = this.ruleRepository.findById(id);
        return rule.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody Rule rule) {
        Optional<Rule> r = this.ruleRepository.findByDescr(rule.getDescr());
        if (r.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            rule = this.ruleRepository.save(rule);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(rule);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> update(@PathVariable String id, @RequestBody Rule rule) {
        Optional<Rule> r = this.ruleRepository.findById(id);
        if (!r.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        r.get().setType(rule.getType());
        r.get().setDescr(rule.getDescr());
        r.get().setErrorMsg(rule.getErrorMsg());
        r.get().setPlainCode(rule.getPlainCode());
        r.get().setRegexp(rule.getRegexp());

        try {
            rule = this.ruleRepository.save(rule);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(rule);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable String id) {
        Optional<Rule> r = this.ruleRepository.findById(id);
        if (!r.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            this.ruleRepository.delete(r.get());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(r);
    }
}
