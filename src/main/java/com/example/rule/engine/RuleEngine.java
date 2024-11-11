package com.example.rule.engine;

import com.example.rule.models.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RuleEngine {

    private final List<Rule> rules;

    public RuleEngine(List<Map<String, Object>> rules) {
        this.rules = deserializeRules(rules);
    }

    private List<Rule> deserializeRules(List<Map<String, Object>> rulesData) {
        List<Rule> auxRules = new ArrayList<>();
        for (Map<String, Object> ruleData : rulesData) {
            auxRules.add(new Rule(ruleData));
        }
        return auxRules;
    }

    public List<Rule> evaluate(Object obj) {
        List<Rule> results = new ArrayList<>();
        for (Rule rule : rules) {
            Rule ruleCopy = deepCopy(rule); // Implement deep copy logic
            ruleCopy.getConditions().evaluate(obj);

            if (ruleCopy.getConditions().isMatch()) {
                results.add(ruleCopy);
            }
        }
        return results;
    }

    private Rule deepCopy(Rule rule) {
        // Implement deep copy logic for Rule
        return new Rule(rule); // Adjust based on your Rule class constructor
    }
}

