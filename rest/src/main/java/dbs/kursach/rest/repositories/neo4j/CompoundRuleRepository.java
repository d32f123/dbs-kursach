package dbs.kursach.rest.repositories.neo4j;

import dbs.kursach.rest.models.neo4j.CompoundRule;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.repository.CrudRepository;

public interface CompoundRuleRepository extends CrudRepository<CompoundRule, Long> {

    @Depth(-1)
    CompoundRule findByTitle(String title);
}
