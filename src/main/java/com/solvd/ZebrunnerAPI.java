package com.solvd;

import static com.solvd.utils.FileUtils.removeInitialSpaces;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.gson.JsonObject;
import com.solvd.domain.TestExcecutionFinishDTO;
import com.solvd.domain.TestExecutionStartDTO;
import com.solvd.domain.TestRunFinishDTO;
import com.solvd.domain.TestStartDTO;
import com.solvd.domain.TokenGenerationDTO;
import com.solvd.utils.FileUtils;
import com.solvd.utils.RequestUpdate;
import com.solvd.utils.ResponseUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class ZebrunnerAPI extends BaseClass {

	private final String endpoint = properties.getProperty("URL");
	private String keyToken = "Authorization";

	public void tokenGeneration() {
		String token = FileUtils.propertyValue("access-token").get("access-token");
		TokenGenerationDTO bodyObject = new TokenGenerationDTO(token);
		String tokenGenerationEndpoint = endpoint
				.concat(FileUtils.readValueInProperties(endpointPath, "ENP_TOKEN_GENERATE"));

		String bodyJson = gson.toJson(bodyObject);

		RequestBody body = RequestBody.create(JSON, bodyJson);
		Request request = new Request.Builder().url(tokenGenerationEndpoint).post(body).build();

		try {
			Response response = client.newCall(request).execute();
			String bodyResponse = response.body().string();

			response.body().close();
			FileUtils.changeProperties("Bearer " + ResponseUtils.splitResponse(bodyResponse, "\"authToken\""),
					"Authorization", tokenPath);

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void testStartRequest(JsonObject endpointData) {

		String projectKey = removeInitialSpaces(FileUtils.propertyValue("project-key").get("project-key"));

		String endpointTestStart = endpoint.concat(RequestUpdate.addQueryParamsValue("ENP_RUN_START", projectKey));

		TestStartDTO bodyObject = new TestStartDTO(endpointData.get("name").getAsString(),
				endpointData.get("framework").getAsString());
		String bodyJson = gson.toJson(bodyObject);

		RequestBody body = RequestBody.create(JSON, bodyJson);

		String keyToken = "Authorization";
		Request request = new Request.Builder().url(endpointTestStart).post(body)
				.addHeader(keyToken, FileUtils.readValueInProperties(tokenPath, "Authorization=Bearer ")).build();

		try {
			Response response = client.newCall(request).execute();
			String bodyResponse = response.body().string();

			response.body().close();

			FileUtils.changeProperties(ResponseUtils.splitResponse(bodyResponse, "\"id\""), "id", idPath);

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void testExecutionStart(JsonObject endpointData) {

		String endpointTestExecutionStart = endpoint.concat(RequestUpdate.completeEndpoint("ENP_EXECUTION", "/tests"));

		TestExecutionStartDTO bodyObject = new TestExecutionStartDTO(endpointData.get("name").getAsString(),
				endpointData.get("className").getAsString(), endpointData.get("methodName").getAsString());

		String bodyJson = gson.toJson(bodyObject);

		RequestBody body = RequestBody.create(JSON, bodyJson);

		String keyToken = "Authorization";
		Request request = new Request.Builder().url(endpointTestExecutionStart).post(body)
				.addHeader(keyToken, FileUtils.readValueInProperties(tokenPath, "Authorization=Bearer ")).build();

		try {
			Response response = client.newCall(request).execute();
			String bodyResponse = response.body().string();

			response.body().close();

			FileUtils.changeProperties(ResponseUtils.splitResponse(bodyResponse, "\"id\""), "test_id", idPath);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void testScreenshotCollectionRequest(File screenshot) {
		String projectId = FileUtils.readValueInProperties(idPath, "id") + "/tests/"
				+ FileUtils.readValueInProperties(idPath, "test_id=") + "/screenshots";
		String endpointScreenshotCollection = endpoint
				.concat(RequestUpdate.addQueryParamsValue("ENP_EXECUTION", projectId));

		BufferedImage bufferedImage;
		MediaType MEDIA_TYPE_HTTP = MediaType.parse("image");

		try {
			bufferedImage = ImageIO.read(screenshot);

			WritableRaster raster = bufferedImage.getRaster();
			DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

			byte[] bodyImage = data.getData();

			RequestBody body = RequestBody.create(MEDIA_TYPE_HTTP, bodyImage);

			Request request = new Request.Builder().url(endpointScreenshotCollection).put(body)
					.addHeader(keyToken, FileUtils.readValueInProperties(tokenPath, "Authorization=Bearer ")).build();

			Response response = client.newCall(request).execute();
			response.body().close();

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}

	}

	public void testExecutionFinishRequest(JsonObject endpointData) {
		String projectId = FileUtils.readValueInProperties(idPath, "id") + "/tests/"
				+ FileUtils.readValueInProperties(idPath, "test_id=");
		String endpointTestExecutionFinishRun = endpoint
				.concat(RequestUpdate.addQueryParamsValue("ENP_EXECUTION", projectId));

		TestExcecutionFinishDTO bodyObject = new TestExcecutionFinishDTO(endpointData.get("result").getAsString(),
				endpointData.get("endedAt").getAsString());
		String bodyJson = gson.toJson(bodyObject);

		RequestBody body = RequestBody.create(JSON, bodyJson);

		String keyToken = "Authorization";
		Request request = new Request.Builder().url(endpointTestExecutionFinishRun).put(body)
				.addHeader(keyToken, FileUtils.readValueInProperties(tokenPath, "Authorization=Bearer ")).build();

		try {
			Response response = client.newCall(request).execute();
			response.body().close();

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void testRunFinishRequest(JsonObject endExecution) {
		String projectId = FileUtils.readValueInProperties(idPath, "id");
		String endpointTestFinishRun = endpoint.concat(RequestUpdate.addQueryParamsValue("ENP_RUN_FINISH", projectId));

		TestRunFinishDTO bodyObject = new TestRunFinishDTO(endExecution.get("endedAt").getAsString());
		String bodyJson = gson.toJson(bodyObject);

		RequestBody body = RequestBody.create(JSON, bodyJson);

		String keyToken = "Authorization";
		Request request = new Request.Builder().url(endpointTestFinishRun).put(body)
				.addHeader(keyToken, FileUtils.readValueInProperties(tokenPath, "Authorization=Bearer ")).build();

		try {
			Response response = client.newCall(request).execute();

			response.body().close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}
}
