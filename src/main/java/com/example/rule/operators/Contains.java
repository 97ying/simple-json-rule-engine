package com.example.rule.operators;

import com.example.rule.models.Condition;
import com.example.rule.models.SimpleCondition;

import java.util.List;

public class Contains extends Operator {
    private static final String id = "contains";

    public Contains(Condition condition) {
        super(condition);
    }

    public static String getId() {
        return id;
    }

    @Override
    public MatchResult match(Object objValue) {
        if (condition instanceof SimpleCondition simpleCondition) {
            boolean contains = false;

            if (objValue instanceof List<?> objListValue && simpleCondition.getValue() instanceof String conditionValue) {
                contains = objListValue.contains(conditionValue);
            }

            return new MatchResult(contains, objValue);
        }
        throw new IllegalArgumentException("Condition must be of type SimpleCondition");
    }
}
