package dbs.kursach.rest.repositories.mongo;

import dbs.kursach.rest.models.mongo.Response;
import dbs.kursach.rest.models.mongo.Rule;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by Yana Tokareva on 13.04.2018.
 */
public interface ResponseRepository extends MongoRepository<Response, String> {
    public Optional<Response> findByPassed(boolean passed);
}
