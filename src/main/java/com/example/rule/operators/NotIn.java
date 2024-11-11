package com.example.rule.operators;

import com.example.rule.models.Condition;
import com.example.rule.models.SimpleCondition;

import java.util.Collection;

public class NotIn extends Operator {
    private static final String id = "not_in";

    public NotIn(Condition condition) {
        super(condition);
    }

    public static String getId() {
        return id;
    }

    @Override
    public MatchResult match(Object objValue) {
        if (condition instanceof SimpleCondition simpleCondition) {
            boolean isNotIn = false;

            if (simpleCondition.getValue() instanceof Collection<?> conditionValue) {
                isNotIn = !conditionValue.contains(objValue);
            }

            return new MatchResult(isNotIn, objValue);
        }
        throw new IllegalArgumentException("Condition must be of type SimpleCondition");
    }
}
