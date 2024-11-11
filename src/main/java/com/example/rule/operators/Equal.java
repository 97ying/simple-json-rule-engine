package com.example.rule.operators;

import com.example.rule.models.Condition;
import com.example.rule.models.SimpleCondition;

import java.util.Objects;

public class Equal extends Operator {
    private static final String id = "equal";

    public Equal(Condition condition) {
        super(condition);
    }

    public static String getId() {
        return id;
    }

    @Override
    public MatchResult match(Object objValue) {
        if (condition instanceof SimpleCondition simpleCondition) {
            boolean isEqual = Objects.equals(simpleCondition.value, objValue);
            return new MatchResult(isEqual, objValue);
        }
        throw new IllegalArgumentException("Condition must be of type SimpleCondition");
    }
}