package com.solvd.domain;

public class ResponseDTO {
    private String accessToken;
    private String runId;
    private String testId;

    private String testIdHeadless;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestIdHeadless() {
        return testIdHeadless;
    }

    public void setTestIdHeadless(String testIdHeadless) {
        this.testIdHeadless = testIdHeadless;
    }
}
