package dbs.kursach.rest.models.neo4j;

import dbs.kursach.rest.models.Validatable;
import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.id.UuidStrategy;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NodeEntity(label="Condition")
public class Condition implements Validatable {

    @Relationship(type="CONDITIONS")
    private List<Condition> subConditions;

    @Id @GeneratedValue(strategy = UuidStrategy.class)
    private Object uuid;

    public Condition() {}

    public List<Condition> getSubConditions() {
        if (subConditions == null) return null;
        return new ArrayList<Condition>(subConditions);
    }

    public void setSubConditions(List<Condition> subConditions) {
        this.subConditions = subConditions;
    }

    public Object getUuid() {
        return uuid;
    }

    public void setUuid(Object uuid) {
        this.uuid = uuid;
    }

    public Condition getSubCondition(int index) { return subConditions.get(index); }
    public void appendSubCondition(Condition condition) {
        if (this.subConditions == null)
            this.subConditions = new ArrayList<>();
        this.subConditions.add(condition);
    }
    public void setSubCondition(int index, Condition condition) { this.subConditions.set(index, condition); }
    public void removeSubCondition(int index) { this.subConditions.remove(index); }

    @Override
    public String validate() {
        if (subConditions == null || subConditions.size() == 0)
            return "";
        for (Condition cond : subConditions) {
            String ret = cond.validate();
            if (ret != null && ret.length() > 0)
                return ret;
        }
        return "";
    }
}