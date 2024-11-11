package com.example.rule.operators;

import com.example.rule.models.Condition;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class OperatorFactory {

    private static final Map<String, Class<? extends Operator>> operatorMap = new HashMap<>();

    static {
        operatorMap.put(Equal.getId(), Equal.class);
        operatorMap.put(NotEqual.getId(), NotEqual.class);
        operatorMap.put(LessThan.getId(), LessThan.class);
        operatorMap.put(LessThanInclusive.getId(), LessThanInclusive.class);
        operatorMap.put(GreaterThan.getId(), GreaterThan.class);
        operatorMap.put(GreaterThanInclusive.getId(), GreaterThanInclusive.class);
        operatorMap.put(In.getId(), In.class);
        operatorMap.put(NotIn.getId(), NotIn.class);
        operatorMap.put(Contains.getId(), Contains.class);
        operatorMap.put(NotContains.getId(), NotContains.class);
    }

    public static Operator createOperator(String operatorId, Condition condition) {
        if (!operatorMap.containsKey(operatorId)) {
            throw new IllegalArgumentException("Specified operator '" + operatorId + "' not found in engine");
        }

        try {
            final Constructor<? extends Operator> declaredConstructor = operatorMap.get(operatorId).getDeclaredConstructor(Condition.class);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(condition);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Class<? extends Operator>> getOperators() {
        return operatorMap;
    }
}
