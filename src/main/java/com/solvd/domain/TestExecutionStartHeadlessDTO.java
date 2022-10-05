package com.solvd.domain;

import com.solvd.utils.DateFormatter;

public class TestExecutionStartHeadlessDTO {
    String name;
    String startedAt;

    public TestExecutionStartHeadlessDTO(String name) {
        this.name = name;
        this.startedAt = DateFormatter.getCurrentTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }
}
