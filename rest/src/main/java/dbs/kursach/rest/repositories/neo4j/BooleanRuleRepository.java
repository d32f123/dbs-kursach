package dbs.kursach.rest.repositories.neo4j;

import dbs.kursach.rest.models.neo4j.BooleanRule;
import org.springframework.data.repository.CrudRepository;

public interface BooleanRuleRepository extends CrudRepository<BooleanRule, Long> {
}