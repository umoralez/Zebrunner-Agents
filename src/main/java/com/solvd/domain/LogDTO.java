package com.solvd.domain;

public class LogDTO {
    
    private String testId;
    private String message;
    private String level;
    private long timestamp;

    public LogDTO() {}
    public LogDTO(String message, String level, long timestamp) {
        this.message = message;
        this.level = level;
        this.timestamp = timestamp;
    }
    
    public String getTestId() {
        return testId;
    }
    public void setTestId(String testId) {
        this.testId = testId;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
