package com.solvd;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.JsonObject;
import com.solvd.domain.ResponseDTO;
import com.solvd.domain.TestExcecutionFinishDTO;
import com.solvd.domain.TestExecutionStartDTO;
import com.solvd.domain.TestExecutionStartHeadlessDTO;
import com.solvd.domain.TestRunFinishDTO;
import com.solvd.domain.TestStartDTO;
import com.solvd.domain.TestStartHeadlessDTO;
import com.solvd.domain.TokenGenerationDTO;
import com.solvd.utils.AgentFileNotFound;
import com.solvd.utils.FileUtils;
import com.solvd.utils.RequestUpdate;
import com.solvd.utils.ResponseUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class ZebrunnerAPI extends BaseClass {

	private final String endpoint = properties.getProperty("URL");
	private final ResponseDTO DATA = new ResponseDTO();
	private static ZebrunnerAPI INSTANCE;

	private ZebrunnerAPI() throws AgentFileNotFound {
		super();
	}

	public void tokenGeneration() throws AgentFileNotFound {

		// String token = FileUtils.propertyValue("access-token").get("access-token");
		String token = agentConfigs.getAccessToken();
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

			DATA.setAccessToken(ResponseUtils.splitResponse(bodyResponse, "\"authToken\""));

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void testStartRequest(JsonObject endpointData) throws AgentFileNotFound {

		// String projectKey =
		// removeInitialSpaces(FileUtils.propertyValue("project-key").get("project-key"));
		String projectKey = agentConfigs.getKeyProject();
		String endpointTestStart = endpoint.concat(RequestUpdate.addQueryParamsValue("ENP_RUN_START", projectKey));

		TestStartDTO bodyObject = new TestStartDTO(endpointData.get("name").getAsString(),
				endpointData.get("framework").getAsString());
		String bodyJson = gson.toJson(bodyObject);

		RequestBody body = RequestBody.create(JSON, bodyJson);

		Request request = new Request.Builder().url(endpointTestStart).post(body)
				.addHeader("Authorization", "Bearer " + DATA.getAccessToken()).build();

		try {
			Response response = client.newCall(request).execute();
			String bodyResponse = response.body().string();

			response.body().close();

			DATA.setRunId(ResponseUtils.splitResponse(bodyResponse, "\"id\""));

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void testStartRequestHeadless(JsonObject endpointData) {
		String endpointTestExecutionHeadless = endpoint
				.concat(FileUtils.readValueInProperties(endpointPath, "ENP_EXECUTION")).concat(DATA.getRunId())
				.concat("/tests?headless=true");

		TestExecutionStartHeadlessDTO bodyObject = new TestExecutionStartHeadlessDTO(
				endpointData.get("name").getAsString());

		String bodyJson = gson.toJson(bodyObject);

		RequestBody body = RequestBody.create(JSON, bodyJson);

		Request request = new Request.Builder().url(endpointTestExecutionHeadless).post(body)
				.addHeader("Authorization", "Bearer " + DATA.getAccessToken()).build();

		try {
			Response response = client.newCall(request).execute();
			String responseBody = response.body().string();

			response.body().close();

			DATA.setTestIdHeadless(ResponseUtils.splitResponse(responseBody, "\"id\""));

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void testExecutionStartHeadless(JsonObject endpointData) {
		String endpointTestExecutionHeadless = endpoint
				.concat(FileUtils.readValueInProperties(endpointPath, "ENP_EXECUTION")).concat(DATA.getRunId())
				.concat("/tests/").concat(DATA.getTestIdHeadless()).concat("?headless=true");

		TestStartHeadlessDTO bodyObject = new TestStartHeadlessDTO(endpointData.get("name").getAsString(),
				endpointData.get("className").getAsString(), endpointData.get("methodName").getAsString());

		String bodyJson = gson.toJson(bodyObject);

		RequestBody body = RequestBody.create(JSON, bodyJson);

		Request request = new Request.Builder().url(endpointTestExecutionHeadless).put(body)
				.addHeader("Authorization", "Bearer " + DATA.getAccessToken()).build();

		try {
			Response response = client.newCall(request).execute();

			response.body().close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void testExecutionStart(JsonObject endpointData) {

		String endpointTestExecutionStart = endpoint
				.concat(FileUtils.readValueInProperties(endpointPath, "ENP_EXECUTION")).concat(DATA.getRunId())
				.concat("/tests");

		TestExecutionStartDTO bodyObject = new TestExecutionStartDTO(endpointData.get("name").getAsString(),
				endpointData.get("className").getAsString(), endpointData.get("methodName").getAsString());

		String bodyJson = gson.toJson(bodyObject);

		RequestBody body = RequestBody.create(JSON, bodyJson);

		Request request = new Request.Builder().url(endpointTestExecutionStart).post(body)
				.addHeader("Authorization", "Bearer " + DATA.getAccessToken()).build();

		try {
			Response response = client.newCall(request).execute();
			String bodyResponse = response.body().string();

			response.body().close();
			DATA.setTestId(ResponseUtils.splitResponse(bodyResponse, "\"id\""));

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 *
	 * @param endpointData
	 * @param headless     specify the test's type (normal or headless)
	 */
	public void testExecutionFinishRequest(JsonObject endpointData, Boolean headless) {

		String endpointTestExecutionFinishRun = endpoint
				.concat(FileUtils.readValueInProperties(endpointPath, "ENP_EXECUTION")).concat(DATA.getRunId())
				.concat("/tests/");

		if (headless) {
			endpointTestExecutionFinishRun += DATA.getTestIdHeadless();
		} else {
			endpointTestExecutionFinishRun += DATA.getTestId();
		}

		TestExcecutionFinishDTO bodyObject = new TestExcecutionFinishDTO(endpointData.get("result").getAsString());
		String bodyJson = gson.toJson(bodyObject);

		RequestBody body = RequestBody.create(JSON, bodyJson);

		Request request = new Request.Builder().url(endpointTestExecutionFinishRun).put(body)
				.addHeader("Authorization", "Bearer " + DATA.getAccessToken()).build();

		try {
			Response response = client.newCall(request).execute();
			response.body().close();

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void testRunFinishRequest() {
		String endpointTestFinishRun = endpoint.concat(FileUtils.readValueInProperties(endpointPath, "ENP_RUN_FINISH"))
				.concat(DATA.getRunId());

		TestRunFinishDTO bodyObject = new TestRunFinishDTO();
		String bodyJson = gson.toJson(bodyObject);

		RequestBody body = RequestBody.create(JSON, bodyJson);

		Request request = new Request.Builder().url(endpointTestFinishRun).put(body)
				.addHeader("Authorization", "Bearer " + DATA.getAccessToken()).build();

		try {
			Response response = client.newCall(request).execute();

			response.body().close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public static ZebrunnerAPI getInstance() throws AgentFileNotFound {
		if (INSTANCE == null) {
			INSTANCE = new ZebrunnerAPI();
		}
		return INSTANCE;
	}

	public void testScreenshotCollectionRequest(String path) {

		byte[] content;
		try {
			content = Files.readAllBytes(Paths.get(path));

			String endpointScreenshotCollection = endpoint
					.concat(FileUtils.readValueInProperties(endpointPath, "ENP_EXECUTION")).concat(DATA.getRunId())
					.concat("/tests/").concat(DATA.getTestId()).concat("/screenshots");

			final MediaType MEDIA_TYPE_HTTP = MediaType.parse("image/png");

			RequestBody body = RequestBody.create(MEDIA_TYPE_HTTP, content);

			Request request = new Request.Builder().url(endpointScreenshotCollection).post(body)
					.addHeader("Authorization", "Bearer " + DATA.getAccessToken()).build();

			Response response = client.newCall(request).execute();
			response.body().close();
		}

		catch (IOException e) {
			LOGGER.error(e.getMessage());

		}
	}
}
