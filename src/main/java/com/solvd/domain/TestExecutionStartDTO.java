package com.solvd.domain;

import com.google.gson.JsonElement;
import com.solvd.utils.DateFormatter;

public class TestExecutionStartDTO {

    String className;
    String name;
    String methodName;
    String startedAt;
    JsonElement labels;

    public TestExecutionStartDTO(String name, String className, String methodName) {
        this.name = name;
        this.className = className;
        this.methodName = methodName;
        this.startedAt = DateFormatter.getCurrentTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public JsonElement getLabels() {
        return labels;
    }

    public void setLabels(JsonElement labels) {
        this.labels = labels;
    }
}
