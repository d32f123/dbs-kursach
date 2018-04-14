package dbs.kursach.rest.models.cassandra;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.datastax.driver.core.DataType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("logs")
public class Log {

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    private UUID id;
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


    public Log() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
}