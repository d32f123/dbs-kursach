package dbs.kursach.rest.repositories.mongo;

import java.util.Optional;

import dbs.kursach.rest.models.mongo.Rule;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RuleRepository extends MongoRepository<Rule, String> {
    public Rule findByType(String firstName);
    public Optional<Rule> findByDescr(String lastName);
}