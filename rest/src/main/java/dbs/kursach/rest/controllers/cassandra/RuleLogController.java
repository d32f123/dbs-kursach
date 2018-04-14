package dbs.kursach.rest.controllers.cassandra;

import dbs.kursach.rest.models.cassandra.Log;
import dbs.kursach.rest.models.cassandra.RuleLog;
import dbs.kursach.rest.models.mongo.Rule;
import dbs.kursach.rest.repositories.cassandra.RuleLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rule-logs")
public class RuleLogController {

    @Autowired
    RuleLogRepository ruleLogRepository;

    @RequestMapping(value = "/{type}/{id}/{timeuuid}", method = RequestMethod.GET)
    ResponseEntity<RuleLog> get(@PathVariable("type") int ruleType,
                             @PathVariable("id") String ruleId,
                             @PathVariable("timeuuid") UUID timeid) {
        Optional<RuleLog> log = this.ruleLogRepository.findById(
                BasicMapId.id("ruleType", ruleType)
                        .with("ruleId", ruleId)
                        .with("timeid", timeid));
        return log.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/rule/{type}/{id}", method = RequestMethod.GET)
    Iterable<RuleLog> getByRuleId(@PathVariable("type") int ruleType, @PathVariable("id") String ruleId) {
        Iterable<RuleLog> logs = this.ruleLogRepository.findByRuleTypeAndRuleId(ruleType, ruleId);
        return logs;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody RuleLog log) {
        try {
            log = this.ruleLogRepository.save(log);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(log);
    }

    @RequestMapping(value = "/{type}/{id}/{timeuuid}", method = RequestMethod.PUT)
    ResponseEntity<?> update(@PathVariable("type") int ruleType,
                             @PathVariable("id") String ruleId,
                             @PathVariable("timeuuid") UUID timeid,
                             @RequestBody RuleLog log) {
        Optional<RuleLog> l = this.ruleLogRepository.findById(BasicMapId.id("ruleType", ruleType)
                .with("ruleId", ruleId)
                .with("timeid", timeid));
        if (!l.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        RuleLog temp = l.get();
        log.setRuleId(temp.getRuleId());
        log.setTimeid(temp.getTimeid());
        log.setRuleType(temp.getRuleType());

        try {
            log = this.ruleLogRepository.save(log);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(log);
    }

    @RequestMapping(value = "/{type}/{id}/{timeuuid}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable("type") int ruleType,
                             @PathVariable("id") String ruleId,
                             @PathVariable("timeuuid") UUID timeid) {
        try {
            this.ruleLogRepository.deleteById(BasicMapId.id("ruleType", ruleType)
                    .with("ruleId", ruleId)
                    .with("timeid", timeid));
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

