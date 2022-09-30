package com.solvd;

import com.google.gson.JsonObject;
import com.solvd.requests.post.TestExecutionStart;
import com.solvd.requests.post.TestStart;
import com.solvd.requests.post.TokenGeneration;
import com.solvd.requests.put.TestRunFinish;
import com.solvd.utils.DateFormatter;

public class EntryPoint {
    public static void main(String[] args) {
//        TokenGeneration endpoint = new TokenGeneration();
//        endpoint.tokenGeneration();
//
//        TestStart endpointTS = new TestStart();
//        JsonObject testDataStart = new JsonObject();
//        testDataStart.addProperty("name", "Test name l1");
//        testDataStart.addProperty("framework", "testng");
//
//        endpointTS.testStartRequest(testDataStart);
//
//        TestRunFinish endpointTF = new TestRunFinish();
//        JsonObject testDataEnd = new JsonObject();
//        testDataEnd.addProperty("endedAt", DateFormatter.getCurrentTime());
//
//        endpointTF.testRunFinishRequest(testDataEnd);
//
        JsonObject endpointTSE = new JsonObject();
        TestExecutionStart testExecutionStart = new TestExecutionStart();

        endpointTSE.addProperty("name", "Test name l1");
        endpointTSE.addProperty("className", "com.name.class");
        endpointTSE.addProperty("methodName", "methodName()");

        testExecutionStart.testExecutionStart(endpointTSE);

    }
    
//TODO:
// - Save the test id returned in the TestExecutionStart (use the response utils)
// - Fix the files structure in resources folder (find some method to replace or/and delete a line in a file)
// - Collect all the endpoints in the same class and add the static modifier



}
