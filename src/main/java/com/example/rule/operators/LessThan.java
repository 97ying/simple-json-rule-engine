package com.example.rule.operators;

import com.example.rule.models.Condition;
import com.example.rule.models.SimpleCondition;

public class LessThan extends Operator {
    private static final String id = "less_than";

    public LessThan(Condition condition) {
        super(condition);
    }

    public static String getId() {
        return id;
    }

    @Override
    public MatchResult match(Object objValue) {
        if (condition instanceof SimpleCondition simpleCondition) {
            boolean isLessThan = false;

            if (simpleCondition.getValue() instanceof Number conditionValue && objValue instanceof Number objNumber) {
                isLessThan = objNumber.doubleValue() < conditionValue.doubleValue();
            }

            return new MatchResult(isLessThan, objValue);
        }
        throw new IllegalArgumentException("Condition must be of type SimpleCondition");
    }
}
