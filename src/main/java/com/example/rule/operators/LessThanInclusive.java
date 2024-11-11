package com.example.rule.operators;

import com.example.rule.models.Condition;
import com.example.rule.models.SimpleCondition;

public class LessThanInclusive extends Operator {
    private static final String id = "less_than_inclusive";

    public LessThanInclusive(Condition condition) {
        super(condition);
    }

    public static String getId() {
        return id;
    }

    @Override
    public MatchResult match(Object objValue) {
        if (condition instanceof SimpleCondition simpleCondition) {
            boolean isLessThanInclusive = false;

            if (simpleCondition.getValue() instanceof Number conditionValue && objValue instanceof Number objNumberValue) {
                isLessThanInclusive = objNumberValue.doubleValue() <= conditionValue.doubleValue();
            }

            return new MatchResult(isLessThanInclusive, objValue);
        }
        throw new IllegalArgumentException("Condition must be of type SimpleCondition");
    }
}