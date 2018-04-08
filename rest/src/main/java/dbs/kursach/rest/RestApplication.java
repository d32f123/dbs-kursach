package dbs.kursach.rest;

import dbs.kursach.rest.models.neo4j.BooleanRule;
import dbs.kursach.rest.models.neo4j.CompoundRule;
import dbs.kursach.rest.models.neo4j.Condition;
import dbs.kursach.rest.models.neo4j.MongoRule;
import dbs.kursach.rest.repositories.neo4j.CompoundRuleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class RestApplication {

    private final static Logger log = LogManager.getLogger();

    // just for testing (to-be-deleted later)
    String conditionToString(Condition rule, int depth) {
        StringBuilder tabSb = new StringBuilder();
        String tab = "";

        for (int i = 0; i < depth; ++i)
            tabSb.append('\t');

        tab = tabSb.toString();

        StringBuilder sb = new StringBuilder();

        if (rule instanceof CompoundRule) {
            CompoundRule compRule = (CompoundRule) rule;
            sb.append(tab).append("Type: Compound\n")
                    .append(tab).append("Title: ").append(compRule.getTitle())
                    .append("\n").append(tab).append("Description: ").append(compRule.getDescription())
                    .append("\n").append(tab).append("Condition: {\n").append(conditionToString(compRule.getCondition(), depth + 1))
                    .append(tab).append("}\n");
        }
        else if (rule instanceof BooleanRule) {
            BooleanRule boolRule = (BooleanRule) rule;
            sb.append(tab).append("Type: Boolean\n")
                    .append(tab).append("Bool: ").append(boolRule.getBoolType()).append('\n')
                    .append(tab).append("Conditions: [\n");
            for (Condition cond :
                    boolRule.getSubConditions()) {
                sb.append(tab).append('\t').append("{\n").append(conditionToString(cond, depth + 2))
                        .append(tab).append('\t').append("},\n");
            }
            sb.append(tab).append("]\n");
        }
        else if (rule instanceof MongoRule) {
            MongoRule simpRule = (MongoRule) rule;
            sb.append(tab).append("Type: MongoRule\n")
                    .append(tab).append("MongoID: ").append(simpRule.getMongoId()).append("\n");
        }
        else
            sb.append(tab).append(rule.toString());

        return sb.toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(CompoundRuleRepository ruleRepository) {
        return args -> {

            log.info("Trying out rule repository");

            CompoundRule rule = ruleRepository.findByTitle("root");

            log.info(conditionToString(rule, 0));
        };
    }
}
