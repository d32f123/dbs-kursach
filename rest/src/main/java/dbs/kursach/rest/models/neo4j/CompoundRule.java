package dbs.kursach.rest.models.neo4j;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;

@NodeEntity(label = "CompoundRule")
public class CompoundRule extends Condition {

    @Property
    private String title;
    @Property
    private String description;

    public CompoundRule() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void appendSubCondition(Condition condition) {
        if (this.getSubConditions() != null && this.getSubConditions().size() > 0)
            throw new IllegalArgumentException("Only 1 subrule is allowed");
        super.appendSubCondition(condition);
    }

    @Override
    public String validate() {
        if (this.title == null || this.title.length() < 1) {
            return "Compound rule title is invalid";
        }
        return super.validate();
    }

}
