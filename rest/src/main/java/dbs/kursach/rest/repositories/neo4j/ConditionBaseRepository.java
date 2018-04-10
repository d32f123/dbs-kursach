package dbs.kursach.rest.repositories.neo4j;

import dbs.kursach.rest.models.neo4j.Condition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ConditionBaseRepository<T extends Condition> extends CrudRepository<T, String> {

}
