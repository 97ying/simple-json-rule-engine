package com.example.rule.operators;

import com.example.rule.models.Condition;

public abstract class Operator {
    protected final Condition condition;

    public Operator(Condition condition) {
        this.condition = condition;
    }

    public abstract MatchResult match(Object objValue);

}
