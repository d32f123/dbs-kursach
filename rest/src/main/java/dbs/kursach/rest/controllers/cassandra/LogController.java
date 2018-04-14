package dbs.kursach.rest.controllers.cassandra;

import dbs.kursach.rest.models.cassandra.Log;
import dbs.kursach.rest.repositories.cassandra.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    LogRepository logRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<Log> get(@PathVariable("id") UUID id) {
        Optional<Log> log = this.logRepository.findById(id);
        return log.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody Log log) {
        Optional<Log> l = this.logRepository.findById(log.getId());
        if (l.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            log = (Log)this.logRepository.save(log);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(log);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Log log) {
        Optional<Log> l = this.logRepository.findById(id);
        if (!l.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        l.get().setId(log.getId());
        l.get().setMessage(log.getMessage());

        try {
            log = this.logRepository.save(log);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(log);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable UUID id) {
        Optional<Log> l = this.logRepository.findById(id);
        if (!l.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            this.logRepository.delete(l.get());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}

