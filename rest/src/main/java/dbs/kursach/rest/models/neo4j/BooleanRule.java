package dbs.kursach.rest.models.neo4j;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity(label="Boolean")
public class BooleanRule extends Condition {

    @Property
    private String description;
    @Property(name="type")
    private BoolType boolType;

    @Relationship(type="CONDITIONS")
    private List<Condition> subConditions;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BoolType getBoolType() {
        return boolType;
    }

    public void setBoolType(BoolType boolType) {
        this.boolType = boolType;
    }

    public List<Condition> getSubConditions() {
        return subConditions;
    }

    public void setSubConditions(List<Condition> subConditions) {
        this.subConditions = subConditions;
    }
}
