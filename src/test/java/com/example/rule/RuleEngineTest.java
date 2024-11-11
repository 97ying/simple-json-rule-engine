package com.example.rule;

import com.example.rule.engine.RuleEngine;
import com.example.rule.models.Rule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class RuleEngineTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testRuleWithAllCondition() throws JsonProcessingException {
        String obj = "{" +
                "        \"person\": {" +
                "            \"name\": \"Santiago\"," +
                "            \"last_name\": \"Alvarez\"" +
                "        }" +
                "    }";

        String rule = "{" +
                "        \"name\": \"basic_rule\"," +
                "        \"description\": \"Basic rule to test the engine\"," +
                "        \"extra\": {" +
                "            \"some_field\": \"some_value\"" +
                "        }," +
                "        \"conditions\": {" +
                "            \"all\": [" +
                "                {" +
                "                    \"path\": \"$.person.name\"," +
                "                    \"value\": \"Santiago\"," +
                "                    \"operator\": \"equal\"" +
                "                }," +
                "                {" +
                "                    \"path\": \"$.person.last_name\"," +
                "                    \"value\": \"Alvarez\"," +
                "                    \"operator\": \"equal\"" +
                "                }" +
                "            ]" +
                "        }" +
                "    }";

        final Map objMap = objectMapper.readValue(obj, Map.class);
        final Map ruleMap = objectMapper.readValue(rule, Map.class);

        RuleEngine engine = new RuleEngine(List.of(ruleMap));

        List<Rule> results = engine.evaluate(objMap);
        assert results.get(0).getConditions().isMatch() : "Condition should match!";
    }

    @Test
    public void testRuleWithAnyCondition() throws JsonProcessingException {
        String obj = "{" +
                "        \"person\": {" +
                "            \"name\": \"Santiago\"," +
                "            \"last_name\": \"Alvarez\"" +
                "        }" +
                "    }";

        String rule = "{" +
                "        \"name\": \"basic_rule\"," +
                "        \"conditions\": {" +
                "            \"any\": [" +
                "                {" +
                "                    \"path\": \"$.person.name\"," +
                "                    \"value\": \"Martin\"," +
                "                    \"operator\": \"equal\"" +
                "                }," +
                "                {" +
                "                    \"path\": \"$.person.last_name\"," +
                "                    \"value\": \"Alvarez\"," +
                "                    \"operator\": \"equal\"" +
                "                }" +
                "            ]" +
                "        }" +
                "    }";

        final Map objMap = objectMapper.readValue(obj, Map.class);
        final Map ruleMap = objectMapper.readValue(rule, Map.class);

        RuleEngine engine = new RuleEngine(List.of(ruleMap));

        List<Rule> results = engine.evaluate(objMap);
        assert results.get(0).getConditions().isMatch() : "Condition should match!";

    }

    @Test
    public void testRuleWithNotCondition() throws JsonProcessingException {
        String obj = "{" +
                "        \"person\": {" +
                "            \"name\": \"Santiago\"," +
                "            \"last_name\": \"Alvarez\"" +
                "        }" +
                "    }";
        String rule = "{" +
                "        \"name\": \"basic_rule\"," +
                "        \"conditions\": {" +
                "            \"not\": { " +
                "                \"all\": [" +
                "                    {" +
                "                        \"path\": \"$.person.name\"," +
                "                        \"value\": \"Martin\"," +
                "                        \"operator\": \"equal\"" +
                "                    }," +
                "                    {" +
                "                        \"path\": \"$.person.last_name\"," +
                "                        \"value\": \"Palermo\"," +
                "                        \"operator\": \"equal\"" +
                "                    }" +
                "                ]" +
                "            }" +
                "        }" +
                "    }";

        final Map objMap = objectMapper.readValue(obj, Map.class);
        final Map ruleMap = objectMapper.readValue(rule, Map.class);

        RuleEngine engine = new RuleEngine(List.of(ruleMap));

        List<Rule> results = engine.evaluate(objMap);
        assertTrue(results.get(0).getConditions().isMatch(), "Condition should match!");

    }

    @Test
    public void testBasicJsonpath() throws JsonProcessingException {

        String obj = "{" +
                "        \"persons\": [" +
                "            {" +
                "                \"name\": \"Lionel\"," +
                "                \"last_name\": \"Messi\"" +
                "            }," +
                "            {" +
                "                \"name\": \"Cristiano\"," +
                "                \"last_name\": \"Ronaldo\"" +
                "            }," +
                "            {" +
                "                \"name\": \"Neymar\"," +
                "                \"last_name\": \"Jr\"" +
                "            }" +
                "        ]" +
                "    }";

        String rule = "{" +
                "        \"name\": \"basic_rule\"," +
                "        \"description\": \"Basic rule to test the engine\"," +
                "        \"conditions\": {" +
                "            \"all\": [" +
                "                {" +
                "                    \"path\": \"$.persons[*].name\"," +
                "                    \"value\": \"Cristiano\"," +
                "                    \"operator\": \"contains\"" +
                "                }" +
                "            ]" +
                "        }" +
                "    }";


        final Map objMap = objectMapper.readValue(obj, Map.class);
        final Map ruleMap = objectMapper.readValue(rule, Map.class);

        RuleEngine engine = new RuleEngine(List.of(ruleMap));

        List<Rule> results = engine.evaluate(objMap);
        assertTrue(results.get(0).getConditions().isMatch(), "Condition should match!");
    }
}
