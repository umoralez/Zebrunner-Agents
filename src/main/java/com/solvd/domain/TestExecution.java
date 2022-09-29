package com.solvd.domain;

public class TestExecution {

    String name;
    String className;
    String methodName;
    String startedAt;

    public TestExecution(String name, String className, String methodName, String startedAt) {
        this.name = name;
        this.className = className;
        this.methodName = methodName;
        this.startedAt = startedAt;
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
}
