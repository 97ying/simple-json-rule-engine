package com.example.rule.operators;

import com.example.rule.models.Condition;
import com.example.rule.models.SimpleCondition;

public class GreaterThanInclusive extends Operator {
    private static final String id = "greater_than_inclusive";


    public GreaterThanInclusive(Condition condition) {
        super(condition);
    }

    public static String getId() {
        return id;
    }

    @Override
    public MatchResult match(Object objValue) {
        if (condition instanceof SimpleCondition simpleCondition) {
            boolean isGreaterThanInclusive = false;

            if (simpleCondition.value instanceof Number && objValue instanceof Number) {
                isGreaterThanInclusive = ((Number) objValue).doubleValue() >= ((Number) simpleCondition.value).doubleValue();
            }

            return new MatchResult(isGreaterThanInclusive, objValue);
        }
        throw new IllegalArgumentException("Condition must be of type SimpleCondition");
    }
}