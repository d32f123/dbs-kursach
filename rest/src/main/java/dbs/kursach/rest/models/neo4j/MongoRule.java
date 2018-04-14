package dbs.kursach.rest.models.neo4j;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Rule")
public class MongoRule extends Condition {

    @Property
    private String mongoId;

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    @Override
    public void appendSubCondition(Condition condition) {
        throw new IllegalArgumentException("No subrules allowed");
    }

    @Override
    public String validate() {
        return super.validate();
    }
}