package com.solvd.domain;

import com.solvd.utils.DateFormatter;

public class TestRunFinishDTO {

    private String endedAt;
    public TestRunFinishDTO(String endedAt) {
        this.endedAt = DateFormatter.getCurrentTime();
    }
    public String getEndedAt() {
        return endedAt;
    }
    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }
}
