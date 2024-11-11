package com.example.rule.models;

import java.util.Map;

public class Rule {
    private final String name;
    private final String description;
    private final Map<String, Object> extra;
    private final Map<String, Object> event;
    private final Map<String, Object> conditionsData;
    private final MultiCondition conditions;


    public Rule(Map<String, Object> data) {
        this.name = validateValue(data.get("name"), String.class, "name");
        this.description = validateValue(data.get("description"), String.class, "description", true);
        this.extra = validateValue(data.get("extra"), Map.class, "extra", true);
        this.event = validateValue(data.get("event"), Map.class, "event", true);

        this.conditionsData = validateValue(data.get("conditions"), Map.class, "conditions");
        this.conditions = new MultiCondition(conditionsData);
    }

    public Rule(Rule rule) {
        this.name = rule.name;
        this.description = rule.description;
        this.extra = rule.extra;
        this.event = rule.event;
        this.conditionsData = rule.conditionsData;
        this.conditions = new MultiCondition(rule.conditionsData);
    }

    public MultiCondition getConditions() {
        return conditions;
    }

    private <T> T validateValue(Object value, Class<T> type, String fieldName) {
        return validateValue(value, type, fieldName, false);
    }

    private <T> T validateValue(Object value, Class<T> type, String fieldName, boolean nullable) {
        if (value == null && !nullable) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
        if (value != null && !type.isInstance(value)) {
            throw new IllegalArgumentException(fieldName + " must be of type " + type.getSimpleName());
        }
        return type.cast(value);
    }
}

