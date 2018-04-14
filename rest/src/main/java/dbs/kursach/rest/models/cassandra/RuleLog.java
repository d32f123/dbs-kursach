package dbs.kursach.rest.models.cassandra;

import com.datastax.driver.core.DataType;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Table("ruleLogs")
public class RuleLog {
    @PrimaryKeyColumn(name = "ruleType", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private int ruleType;

    @PrimaryKeyColumn(name = "ruleId", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String ruleId;

    @CassandraType(type = DataType.Name.UUID)
    @PrimaryKeyColumn(name = "timeid", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private UUID timeid;
    @Column
    private String message;
    @Column
    private String level;
    @Column
    private String marker;
    @Column
    private String logger;
    @Column
    private Timestamp timestamp;
    @Column
    private Map<String, String> mdc;
    @Column
    private List<String> ndc;

    public RuleLog() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getMdc() {
        return mdc;
    }

    public void setMdc(Map<String, String> mdc) {
        this.mdc = mdc;
    }

    public List<String> getNdc() {
        return ndc;
    }

    public void setNdc(List<String> ndc) {
        this.ndc = ndc;
    }

    public int getRuleType() {
        return ruleType;
    }

    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public UUID getTimeid() {
        return timeid;
    }

    public void setTimeid(UUID timeid) {
        this.timeid = timeid;
    }
}