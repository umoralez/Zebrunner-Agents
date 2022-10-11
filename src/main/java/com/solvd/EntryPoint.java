package com.solvd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;

public class EntryPoint {
    public static void main(String[] args) {

        final ZebrunnerAPI API = ZebrunnerAPI.getInstance();
        final Logger LOGGER = LogManager.getLogger();
        API.tokenGeneration();

        JsonObject testDataStart = new JsonObject();
        testDataStart.addProperty("name", "Headless");
        testDataStart.addProperty("framework", "testng");

        API.testStartRequest(testDataStart);


        //region HEADLESS REQUESTS
        JsonObject endpointTSH = new JsonObject();
        endpointTSH.addProperty("name", "Test headless");

        API.testStartRequestHeadless(endpointTSH);

        JsonObject endpointTSEH = new JsonObject();
        endpointTSEH.addProperty("name", "Test headless");
        endpointTSEH.addProperty("className", "com.name.class");
        endpointTSEH.addProperty("methodName", "methodName()");

        API.testExecutionStartHeadless(endpointTSEH);
        
        JsonObject testEFDH = new JsonObject();
        testEFDH.addProperty("result", "PASSED");

        API.testExecutionFinishRequest(testEFDH, true);
        
        //endregion

        JsonObject endpointTSE = new JsonObject();
        endpointTSE.addProperty("name", "Test l2");
        endpointTSE.addProperty("className", "com.name.class");

        endpointTSE.addProperty("methodName", "methodName()");
        API.testExecutionStart(endpointTSE);
        
        LOGGER.info("test start");

        JsonObject testEFD = new JsonObject();
        testEFD.addProperty("result", "FAILED");

        API.testExecutionFinishRequest(testEFD, false);

        API.testRunFinishRequest();
    }
}
