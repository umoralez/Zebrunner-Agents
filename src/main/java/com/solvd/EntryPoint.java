package com.solvd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.solvd.utils.AgentFileNotFound;
import com.solvd.utils.DateFormatter;

public class EntryPoint {

	public static void main(String[] args) throws AgentFileNotFound {

		final ZebrunnerAPI API = ZebrunnerAPI.getInstance();

		final Logger LOGGER = LogManager.getLogger();

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
		LOGGER.info("test start");

//		Screenshot screenshot = new Screenshot();
//		screenshot.takeScreenshot();
//		API.testScreenshotCollectionRequest(screenshot.getContent(), screenshot.getTimeData());

		JsonObject testRunLabels = new JsonObject();
		JsonArray labelsArray = new JsonArray();
		// Mock label 1
		JsonObject label1 = new JsonObject();
		label1.addProperty("Features", "Test with screenshot");
		labelsArray.add(label1);
		// Mock label 2
		JsonObject label2 = new JsonObject();
		label2.addProperty("Group", "Regression");
		labelsArray.add(label2);
		testRunLabels.add("items", labelsArray);
		// Api call
		API.testExecutionLabelRequest(testRunLabels);
		// endregion Labels Execution Request

		JsonObject testRunLabels = new JsonObject();
		JsonArray labelsArray = new JsonArray();
		// Mock label 1
		JsonObject label1 = new JsonObject();
		label1.addProperty("Features", "Test with screenshot");
		labelsArray.add(label1);
		// Mock label 2
		JsonObject label2 = new JsonObject();
		label2.addProperty("Group", "Regression");
		labelsArray.add(label2);
		testRunLabels.add("items", labelsArray);
		// Api call
		API.testExecutionLabelRequest(testRunLabels);
		// endregion Labels Execution Request

		JsonObject testExecutionFinishedData = new JsonObject();
		testExecutionFinishedData.addProperty("result", "PASSED");
		testExecutionFinishedData.addProperty("endedAt", DateFormatter.getCurrentTime());

		API.testExecutionFinishRequest(testExecutionFinishedData, false);

		JsonObject testRunFinishData = new JsonObject();
		testRunFinishData.addProperty("endedAt", DateFormatter.getCurrentTime());
		API.testRunFinishRequest();

		// region TestExecutionFinishRequest
		JsonObject testEFD = new JsonObject();
		testEFD.addProperty("result", "PASSED");

		LOGGER.info("test start");
		API.testExecutionFinishRequest(testEFD, false);

		// endregion

		API.testRunFinishRequest();
	}

}