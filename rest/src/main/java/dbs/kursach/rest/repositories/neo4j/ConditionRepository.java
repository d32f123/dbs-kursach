package dbs.kursach.rest.repositories.neo4j;

import dbs.kursach.rest.models.neo4j.Condition;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ConditionRepository extends ConditionBaseRepository<Condition> {

    @Query("MATCH (m:Condition) WHERE m.uuid = {secondId}\n" +
            "MATCH (n:Condition) WHERE n.uuid = {firstId} AND NOT EXISTS((n)<-[:CONDITIONS*..]->(m))\n" +
            "CREATE (n)-[rel:CONDITIONS]->(m)\n" +
            "RETURN true")
    Optional<Object> linkRules(@Param("firstId") String firstId, @Param("secondId") String secondId);

    @Query("MATCH (n:Condition)\n" +
            "WHERE n.uuid = {id}\n" +
            "OPTIONAL MATCH (n)-[:CONDITIONS*..]->(m1:Condition)-[:CONDITIONS*..]->(m:CompoundRule)\n" +
            "WHERE NOT m1:CompoundRule\n" +
            "DETACH DELETE n, m1\n" +
            "RETURN true")
    Optional<Object> removeRule(@Param("id") String id);

    @Query("MATCH (n:Condition)<-[rel:CONDITIONS]->(m:Condition)\n" +
            "WHERE n.uuid = {firstId} AND m.uuid = {secondId}\n" +
            "DELETE rel\n" +
            "RETURN true")
    Optional<Object> unlinkRukes(@Param("firstId") String firstId, @Param("secondId") String secondId);
}
