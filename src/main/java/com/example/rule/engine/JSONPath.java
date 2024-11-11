package com.example.rule.engine;

import com.example.rule.exceptions.JSONPathValueNotFound;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.util.ArrayList;
import java.util.List;

public class JSONPath {
    private final String originalPath;
    private final String jsonPathExpression;

    public JSONPath(String path) {
        this.originalPath = path;
        this.jsonPathExpression = path; // Assuming this is the expression used with JsonPath library
    }

    public Object getValueFrom(Object obj) throws JSONPathValueNotFound {
        ReadContext ctx = JsonPath.parse(obj);
        final Object value = ctx.read(jsonPathExpression);

        List<Object> result = new ArrayList<>();
        if (value instanceof List<?>) {
            result.addAll((List<?>) value);
        } else if (value instanceof String) {
            result.add(value);
        }

        if (result.isEmpty()) {
            throw new JSONPathValueNotFound("Value not found at path " + originalPath);
        }

        if (result.size() == 1) {
            return result.get(0);
        }

        return result;
    }
}
