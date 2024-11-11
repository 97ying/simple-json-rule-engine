package com.example.rule.operators;

import com.example.rule.models.Condition;
import com.example.rule.models.SimpleCondition;

import java.util.Collection;

public class In extends Operator {
    private static final String id = "in";

    public In(Condition condition) {
        super(condition);
    }

    public static String getId() {
        return id;
    }

    @Override
    public MatchResult match(Object objValue) {
        if (condition instanceof SimpleCondition simpleCondition) {
            boolean isIn = false;

            if (simpleCondition.value instanceof Collection) {
                isIn = ((Collection<?>) simpleCondition.value).contains(objValue);
            }

            return new MatchResult(isIn, objValue);
        }
        throw new IllegalArgumentException("Condition must be of type SimpleCondition");
    }
}
