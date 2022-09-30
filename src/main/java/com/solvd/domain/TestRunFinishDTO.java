package com.solvd.domain;

public class TestRunFinishDTO {

    private String endedAt;

    public TestRunFinishDTO(String endedAt) {
        this.endedAt = endedAt;
    }
    public String getEndedAt() {
        return endedAt;
    }
    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }
}
