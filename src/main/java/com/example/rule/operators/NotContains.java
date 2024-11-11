package com.example.rule.operators;

import com.example.rule.models.Condition;
import com.example.rule.models.SimpleCondition;

public class NotContains extends Operator {
    private static final String id = "not_contains";

    public NotContains(Condition condition) {
        super(condition);
    }

    public static String getId() {
        return id;
    }

    @Override
    public MatchResult match(Object objValue) {
        if (condition instanceof SimpleCondition simpleCondition) {
            boolean notContains = false;

            if (objValue instanceof String && simpleCondition.value instanceof String) {
                notContains = !((String) objValue).contains((String) simpleCondition.value);
            }

            return new MatchResult(notContains, objValue);
        }
        throw new IllegalArgumentException("Condition must be of type SimpleCondition");
    }
}
