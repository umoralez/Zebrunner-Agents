package com.solvd;

import com.google.gson.JsonObject;
import com.solvd.utils.DateFormatter;

public class EntryPoint {
    public static void main(String[] args) {

        final ZebrunnerAPI API = new ZebrunnerAPI();

        API.tokenGeneration();

        JsonObject testDataStart = new JsonObject();
        testDataStart.addProperty("name", "Test name l2");
        testDataStart.addProperty("framework", "testng");

        API.testStartRequest(testDataStart);

        JsonObject endpointTSE = new JsonObject();
        endpointTSE.addProperty("name", "Test name l1");
        endpointTSE.addProperty("className", "com.name.class");
        endpointTSE.addProperty("methodName", "methodName()");

        API.testExecutionStart(endpointTSE);


        JsonObject testExecutionFinishedData = new JsonObject();
        testExecutionFinishedData.addProperty("result", "PASSED");
        testExecutionFinishedData.addProperty("endedAt", DateFormatter.getCurrentTime());

        API.testExecutionFinishRequest(testExecutionFinishedData);


        JsonObject testRunFinishData = new JsonObject();
        testRunFinishData.addProperty("endedAt", DateFormatter.getCurrentTime());
        API.testRunFinishRequest(testRunFinishData);


    }
}
