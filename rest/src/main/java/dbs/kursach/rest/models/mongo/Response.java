package dbs.kursach.rest.models.mongo;

import dbs.kursach.rest.models.mongo.Rule;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

/**
 * Created by Yana Tokareva on 13.04.2018.
 */
public class Response {
    @DBRef
    private List<Rule> rules;

    @Id
    private String id;
    private boolean passed;

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
