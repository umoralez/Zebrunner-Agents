package com.solvd.domain;

import com.solvd.utils.DateFormatter;

public class TestStartDTO {
    private String projectKey;
    private String name;
    private String framework;
    private String startedAt;

    public TestStartDTO() {
    }

    public TestStartDTO(String projectKey, String name, String framework, String startedAt) {
        this.projectKey = projectKey;
        this.name = name;
        this.framework = framework;
        this.startedAt = startedAt;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getStartedAt() {

        return DateFormatter.getCurrentTime();
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }
}
