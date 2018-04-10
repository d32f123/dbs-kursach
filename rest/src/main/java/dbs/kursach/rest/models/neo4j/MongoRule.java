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

    @Override
    public void appendSubCondition(Condition condition) {
        throw new IllegalArgumentException("No subrules allowed");
    }

    @Override
    public String validate() {
        if (this.mongoId == 0) {
            return "Invalid mongo id";
        }
        return super.validate();
    }
}