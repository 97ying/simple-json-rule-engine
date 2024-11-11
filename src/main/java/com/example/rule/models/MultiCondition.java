package com.example.rule.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultiCondition extends Condition {
    private List<Condition> all;
    private List<Condition> any;
    private Condition not;

    public MultiCondition(Map<?, ?> data) {
        super();

        int conditionCount = 0;
        if (data.containsKey("any") && !((List<?>) data.get("any")).isEmpty()) {
            conditionCount++;
            this.any = validateConditions((List<?>) data.get("any"));
        }
        if (data.containsKey("all") && !((List<?>) data.get("all")).isEmpty()) {
            conditionCount++;
            this.all = validateConditions((List<?>) data.get("all"));
        }
        if (data.containsKey("not") && !((Map<?, ?>) data.get("not")).isEmpty()) {
            conditionCount++;
            this.not = validateNotCondition((Map<?, ?>) data.get("not"));
        }

        if (conditionCount != 1) {
            throw new IllegalArgumentException("Only one of any, all or not can be defined");
        }
    }

    private List<Condition> validateConditions(List<?> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        List<Condition> conditionDataList = new ArrayList<>();

        data.forEach(conditionData -> {
            try {
                conditionDataList.add(new SimpleCondition((Map<?, ?>) conditionData));
            } catch (IllegalArgumentException e) {
                conditionDataList.add(new MultiCondition((Map<?, ?>) conditionData));
            }
        });

        return conditionDataList;
    }

    private Condition validateNotCondition(Map<?, ?> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }

        try {
            return new SimpleCondition(data);
        } catch (IllegalArgumentException e) {
            return new MultiCondition(data);
        }
    }

    @Override
    public void evaluate(Object obj) {
        if (any != null) {
            evaluateAny(obj);
        } else if (all != null) {
            evaluateAll(obj);
        } else {
            evaluateNot(obj);
        }
    }

    private void evaluateAny(Object obj) {
        for (Condition cond : any) {
            cond.evaluate(obj);
            if (cond.match) {
                this.match = true;
                return;
            }
        }
        this.match = false;
    }

    private void evaluateAll(Object obj) {
        for (Condition cond : all) {
            cond.evaluate(obj);
            if (!cond.match) {
                this.match = false;
                return;
            }
        }
        this.match = true;
    }

    private void evaluateNot(Object obj) {
        if (not != null) {
            not.evaluate(obj);
            this.match = !not.match;
        }
    }
}
