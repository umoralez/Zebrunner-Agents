package com.solvd.domain;

import com.solvd.utils.DateFormatter;

public class TestStartHeadlessDTO {

    String className;
    String name;
    String methodName;

    public TestStartHeadlessDTO(String name, String className, String methodName) {
        this.name = name;
        this.className = className;
        this.methodName = methodName;
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

}
