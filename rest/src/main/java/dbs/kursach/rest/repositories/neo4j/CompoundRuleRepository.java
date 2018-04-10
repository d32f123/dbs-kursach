package dbs.kursach.rest.repositories.neo4j;

import dbs.kursach.rest.models.neo4j.CompoundRule;
import org.springframework.data.neo4j.annotation.Depth;

import java.util.Optional;

public interface CompoundRuleRepository extends ConditionBaseRepository<CompoundRule> {
    @Depth(-1)
    Optional<CompoundRule> findByTitle(String title);
}
