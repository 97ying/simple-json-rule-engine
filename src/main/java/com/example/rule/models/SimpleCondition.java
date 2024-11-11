package com.example.rule.models;

import com.example.rule.engine.JSONPath;
import com.example.rule.exceptions.JSONPathValueNotFound;
import com.example.rule.operators.MatchResult;
import com.example.rule.operators.Operator;
import com.example.rule.operators.OperatorFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleCondition extends Condition {
    private final Operator operator;
    private final JSONPath path;
    public Object value;
    //    private Map<String, Object> params;
    private Object matchDetail;

    public SimpleCondition(Map<?, ?> data) {
        super();
        this.operator = validateOperator(data);
        this.path = validatePath(data);
        this.value = data.get("value");
//        this.params = (Map<String, Object>) data.getOrDefault("params", Map.of());
        this.matchDetail = null;
    }

    private JSONPath validatePath(Map<?, ?> data) {
        if (data.containsKey("path")) {
            return new JSONPath((String) data.get("path"));
        }
        return null;
    }

    private Operator validateOperator(Map<?, ?> data) {
        if (!data.containsKey("operator")) {
            throw new IllegalArgumentException("Operator attribute missing");
        }

        return OperatorFactory.createOperator((String) data.get("operator"), this);
    }

    private Object objToDict(Object obj) {
        if (obj instanceof Map<?, ?> map) {
            return map.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> objToDict(e.getValue())
                    ));
        }
        if (obj instanceof List<?> list) {
            return list.stream().map(this::objToDict).collect(Collectors.toList());
        }
        return obj;
    }

    @Override
    public void evaluate(Object obj) {
        try {
            Map<String, Object> objMap = (Map<String, Object>) obj;
            Object pathObj = (path != null) ? path.getValueFrom(objMap) : objMap;
            MatchResult matchResult = operator.match(pathObj);
            this.match = matchResult.matched;
            this.matchDetail = objToDict(matchResult.details);
        } catch (JSONPathValueNotFound e) {
            this.match = false;
            this.matchDetail = e.getMessage();
        }
    }
}
