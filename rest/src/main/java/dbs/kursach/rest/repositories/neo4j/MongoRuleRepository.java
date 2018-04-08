package dbs.kursach.rest.repositories.neo4j;

import dbs.kursach.rest.models.neo4j.MongoRule;
import org.springframework.data.repository.CrudRepository;

public interface MongoRuleRepository extends CrudRepository<MongoRule, Long> {
}