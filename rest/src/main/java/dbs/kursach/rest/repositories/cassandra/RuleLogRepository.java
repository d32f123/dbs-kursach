package dbs.kursach.rest.repositories.cassandra;

import dbs.kursach.rest.models.cassandra.RuleLog;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RuleLogRepository extends CassandraRepository<RuleLog, MapId> {
    Iterable<RuleLog> findByRuleTypeAndRuleId(int ruleType, String ruleId);
}