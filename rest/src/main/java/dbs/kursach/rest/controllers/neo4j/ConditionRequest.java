package dbs.kursach.rest.controllers.neo4j;

import dbs.kursach.rest.models.neo4j.*;

import java.util.List;
import java.util.UUID;

public class ConditionRequest {

    private UUID uuid;
    private List<Condition> subConditions = null;
    private String title = null;
    private String description = null;
    private BoolType boolType = BoolType.UNKNOWN;
    private Long mongoId = 0L;

    public ConditionRequest() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<Condition> getSubConditions() {
        return subConditions;
    }

    public void setSubConditions(List<Condition> subConditions) {
        this.subConditions = subConditions;
    }

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

    public BoolType getBoolType() {
        return boolType;
    }

    public void setBoolType(BoolType boolType) {
        this.boolType = boolType;
    }

    public Long getMongoId() {
        return mongoId;
    }

    public void setMongoId(Long mongoId) {
        this.mongoId = mongoId;
    }

    public Condition transform(boolean useUuid) {
        if (boolType != BoolType.UNKNOWN) {
            BooleanRule rule = new BooleanRule();
            rule.setBoolType(this.boolType);
            if (useUuid)
                rule.setUuid(this.uuid);
            rule.setSubConditions(this.subConditions);
            return rule;
        } else if (title != null && !title.isEmpty()) {
            CompoundRule rule = new CompoundRule();
            rule.setDescription(this.description);
            rule.setTitle(this.title);
            if (useUuid)
                rule.setUuid(this.uuid);
            rule.setSubConditions(this.subConditions);
            return rule;
        } else if (mongoId != -1L) {
            MongoRule rule = new MongoRule();
            rule.setMongoId(this.mongoId);
            if (useUuid)
                rule.setUuid(this.uuid);
            rule.setSubConditions(this.subConditions);
            return rule;
        } else {
            throw new IllegalArgumentException("Invalid condition request");
        }
    }
}