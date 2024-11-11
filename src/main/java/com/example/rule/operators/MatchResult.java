package com.example.rule.operators;

public class MatchResult {
    public boolean matched;
    public Object details;

    public MatchResult(boolean matched, Object details) {
        this.matched = matched;
        this.details = details;
    }
}
