package dbs.kursach.rest.models.neo4j;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Rule")
public class MongoRule extends Condition {

    @Property
    private Long mongoId;

    public Long getMongoId() {
        return mongoId;
    }

    public void setMongoId(Long mongoId) {
        this.mongoId = mongoId;
    }
}