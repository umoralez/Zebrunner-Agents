package com.solvd.domain;

import com.solvd.utils.DateFormatter;
import com.solvd.utils.FileUtils;

import java.text.ParseException;
import java.time.OffsetDateTime;
import java.util.Date;

public class TestStartDTO {
    private String name;
    private String framework;
    private OffsetDateTime startedAt;

    public TestStartDTO() {
    }

    public TestStartDTO(String name, String framework, String startedAt) {
        this.name = name;
        this.framework = framework;
        this.startedAt = DateFormatter.getCurrentTime(startedAt);
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

    public OffsetDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(OffsetDateTime startedAt) {
        this.startedAt = startedAt;
    }
}
