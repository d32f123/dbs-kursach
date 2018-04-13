package dbs.kursach.rest;

import dbs.kursach.rest.models.neo4j.*;
import dbs.kursach.rest.repositories.neo4j.BooleanRuleRepository;
import dbs.kursach.rest.repositories.neo4j.CompoundRuleRepository;
import dbs.kursach.rest.repositories.neo4j.ConditionRepository;
import dbs.kursach.rest.repositories.neo4j.MongoRuleRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestApplicationTests {

    @Autowired
    private CompoundRuleRepository compoundRuleRepository;
    @Autowired
    private ConditionRepository conditionRepository;
    @Autowired
    private MongoRuleRepository mongoRuleRepository;
    @Autowired
    private BooleanRuleRepository booleanRuleRepository;

    public RestApplicationTests() {}

    private static final long MAX_NODES = 2000000L;
    private static final Random rand = new Random();
    private static final Logger log = LogManager.getLogger();

    private Condition generateCompoundRule(int nodes) {
        log.traceEntry("Nodes: {}", nodes);
        CompoundRule rule = new CompoundRule();
        rule.setTitle(UUID.randomUUID().toString());
        --nodes;


        if (nodes == 1)
            rule.appendSubCondition(generateMongoRule());
        else
            rule.appendSubCondition(generateSubRule(nodes));
        log.traceExit();
        return rule;
    }

    private Condition generateMongoRule() {
        log.traceEntry();
        MongoRule rule = new MongoRule();
        rule.setMongoId(rand.nextLong());
        log.traceExit();
        return rule;
    }

    private Condition generateSubRule(int nodes) {
        log.traceEntry("Nodes: {}", nodes);
        BoolType ruleType;
        switch (rand.nextInt(3)) {
            case 0: ruleType = BoolType.AND;
                break;
            case 1: ruleType = BoolType.OR;
                break;
            case 2: ruleType = BoolType.NOT;
                break;
            default: ruleType = BoolType.UNKNOWN;
        }

        BooleanRule rule = new BooleanRule();
        rule.setBoolType(ruleType);
        // count ourselves
        --nodes;
        while (nodes > 0) {
            switch (ruleType) {
                case AND: case OR:
                    if (nodes <= 1) {
                        rule.appendSubCondition(generateMongoRule());
                        nodes = 0;
                        break;
                    }
                    int toGenerate;
                    if (nodes == 2)
                        toGenerate = 2;
                    else
                        toGenerate = rand.nextInt(nodes - 2) + 2;
                    if (rand.nextBoolean()) {
                        rule.appendSubCondition(generateCompoundRule(toGenerate));
                        nodes -= toGenerate;
                    } else {
                        rule.appendSubCondition(generateSubRule(toGenerate));
                        nodes -= toGenerate;
                    }
                    break;
                case NOT:
                    if (nodes > 1) {
                        rule.appendSubCondition(generateSubRule(nodes));
                        nodes = 0;
                    } else {
                        rule.appendSubCondition(generateMongoRule());
                        nodes -= 1;
                    }
                    break;
                default:
                    throw new NotImplementedException("ok");
            }
        }
        log.traceExit();
        return rule;
    }

    private static final int MIN_TREE_SIZE = 4;
    private static final int MAX_TREE_SIZE = 30;
    private static final int TREE_DIFF = MAX_TREE_SIZE - MIN_TREE_SIZE;
    @Test
    public void contextLoads() {

        long i = MAX_NODES;
        while (i > 0) {
            log.info("{} NODES LEFT TO GENERATE", i);
            CompoundRule rule = new CompoundRule();
            rule.setTitle(UUID.randomUUID().toString());

            --i;

            int toGenerate = i < MAX_TREE_SIZE ? (int)i : rand.nextInt(TREE_DIFF) + MIN_TREE_SIZE;

            log.info("Generating rule with {} nodes", toGenerate);
            rule.appendSubCondition(generateSubRule(toGenerate));
            this.compoundRuleRepository.save(rule);
            log.info("Generated rule with id: {}", rule.getUuid());
            i -= toGenerate;
        }
    }

}
