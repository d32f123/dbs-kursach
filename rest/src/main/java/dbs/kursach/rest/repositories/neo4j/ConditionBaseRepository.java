package dbs.kursach.rest.repositories.neo4j;

import dbs.kursach.rest.models.neo4j.Condition;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface ConditionBaseRepository<T extends Condition> extends PagingAndSortingRepository<T, String> {

}
