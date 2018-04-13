package dbs.kursach.rest.controllers.mongo;

import dbs.kursach.rest.models.mongo.Response;
import dbs.kursach.rest.repositories.mongo.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Yana Tokareva on 13.04.2018.
 */

@RestController
@RequestMapping("/responses")
public class ResponseController  {
    @Autowired
    ResponseRepository responseRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<Response> get(@PathVariable("id") String id) {
        Optional<Response> response = this.responseRepository.findById(id);
        return response.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody Response response) {
        Optional<Response> r = this.responseRepository.findByPassed(response.isPassed());
        if (r.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            response = this.responseRepository.save(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> update(@PathVariable String id, @RequestBody Response response) {
        Optional<Response> r = this.responseRepository.findById(id);
        if (!r.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        r.get().setPassed(response.isPassed());

        try {
            response = this.responseRepository.save(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable String id) {
        Optional<Response> r = this.responseRepository.findById(id);
        if (!r.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            this.responseRepository.delete(r.get());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(r);
    }
}
