package com.example.rule.models;


public abstract class Condition {
    protected boolean match;

    public Condition() {
        this.match = false;
    }

    public abstract void evaluate(Object obj);

    public boolean isMatch() {
        return match;
    }

}

