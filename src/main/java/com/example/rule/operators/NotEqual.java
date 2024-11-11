package com.example.rule.operators;

import com.example.rule.models.Condition;
import com.example.rule.models.SimpleCondition;

import java.util.Objects;

public class NotEqual extends Operator {
    private static final String id = "not_equal";

    public NotEqual(Condition condition) {
        super(condition);
    }

    public static String getId() {
        return id;
    }

    @Override
    public MatchResult match(Object objValue) {
        // 确保 condition 是 SimpleCondition 类型，以访问 value
        if (condition instanceof SimpleCondition simpleCondition) {
            boolean isNotEqual = !Objects.equals(simpleCondition.getValue(), objValue);
            return new MatchResult(isNotEqual, objValue);
        }
        throw new IllegalArgumentException("Condition must be of type SimpleCondition");
    }
}
