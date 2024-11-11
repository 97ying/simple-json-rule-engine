# simple-json-rule-engine
A rule engine where rules are defined in JSON format. The syntax of the rules belongs to the json-rules-engine javascript library though it contains some changes to make it more powerfull.
### Inspired and coding referenced by [python-rule-engine](https://github.com/santalvarez/python-rule-engine)
## Quick Example
You can use the JUnit to test the rule engine
```Java
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
```

## Rule Format
Find more info about the rules [here](doc/rules.md).
