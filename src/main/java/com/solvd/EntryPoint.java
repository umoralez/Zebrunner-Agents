package com.solvd;

import com.google.gson.JsonObject;
import com.solvd.utils.AgentFileNotFound;
import com.solvd.utils.DateFormatter;

public class EntryPoint {

	public static void main(String[] args) throws AgentFileNotFound {

		final ZebrunnerAPI API = ZebrunnerAPI.getInstance();

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

		API.testScreenshotCollectionRequest(System.getProperty("user.home").concat("/Desktop/screenshot.png"));

		JsonObject testExecutionFinishedData = new JsonObject();
		testExecutionFinishedData.addProperty("result", "PASSED");
		testExecutionFinishedData.addProperty("endedAt", DateFormatter.getCurrentTime());

		API.testExecutionFinishRequest(testExecutionFinishedData, false);

		JsonObject testRunFinishData = new JsonObject();
		testRunFinishData.addProperty("endedAt", DateFormatter.getCurrentTime());
		API.testRunFinishRequest();

	}

}
