package dbs.kursach.rest.repositories.neo4j;

import dbs.kursach.rest.models.neo4j.Condition;
import org.springframework.data.repository.CrudRepository;

public interface ConditionRepository extends CrudRepository<Condition, Long> {
}
