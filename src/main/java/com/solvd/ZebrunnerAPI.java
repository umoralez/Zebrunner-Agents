package com.solvd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.solvd.domain.LabelItemDTO;
import com.solvd.domain.LogDTO;
import com.solvd.domain.ResponseDTO;
import com.solvd.domain.TestExcecutionFinishDTO;
import com.solvd.domain.TestExecutionStartDTO;
import com.solvd.domain.TestExecutionStartHeadlessDTO;
import com.solvd.domain.TestRunFinishDTO;
import com.solvd.domain.TestRunLabelsDTO;
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

	public ResponseDTO getDATA() {
		return DATA;
	}

	private ZebrunnerAPI() throws AgentFileNotFound {
		super();
	}

	public void tokenGeneration() {

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

	public void testStartRequest(TestStartDTO bodyObject) {

		String projectKey = agentConfigs.getKeyProject();
		String endpointTestStart = endpoint.concat(RequestUpdate.addQueryParamsValue("ENP_RUN_START", projectKey));

		RequestBody body = RequestBody.create(JSON, gson.toJson(bodyObject));

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

	public void testStartRequestHeadless(TestExecutionStartHeadlessDTO bodyObject) {
		String endpointTestExecutionHeadless = endpoint
				.concat(FileUtils.readValueInProperties(endpointPath, "ENP_EXECUTION")).concat(DATA.getRunId())
				.concat("/tests?headless=true");

		RequestBody body = RequestBody.create(JSON, gson.toJson(bodyObject));

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

	public void testExecutionStartHeadless(TestStartHeadlessDTO bodyObject) {
		String endpointTestExecutionHeadless = endpoint
				.concat(FileUtils.readValueInProperties(endpointPath, "ENP_EXECUTION")).concat(DATA.getRunId())
				.concat("/tests/").concat(DATA.getTestIdHeadless()).concat("?headless=true");

		RequestBody body = RequestBody.create(JSON, gson.toJson(bodyObject));

		Request request = new Request.Builder().url(endpointTestExecutionHeadless).put(body)
				.addHeader("Authorization", "Bearer " + DATA.getAccessToken()).build();

		try {
			Response response = client.newCall(request).execute();

			response.body().close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void testExecutionStart(TestExecutionStartDTO bodyObject) {

		String endpointTestExecutionStart = endpoint
				.concat(FileUtils.readValueInProperties(endpointPath, "ENP_EXECUTION")).concat(DATA.getRunId())
				.concat("/tests");

		RequestBody body = RequestBody.create(JSON, gson.toJson(bodyObject));

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
	 * @param headless specify the test's type (normal or headless)
	 */
	public void testExecutionFinishRequest(TestExcecutionFinishDTO bodyObject, Boolean headless) {

		String endpointTestExecutionFinishRun = endpoint
				.concat(FileUtils.readValueInProperties(endpointPath, "ENP_EXECUTION")).concat(DATA.getRunId())
				.concat("/tests/");

		if (headless) {
			endpointTestExecutionFinishRun += DATA.getTestIdHeadless();
		} else {
			endpointTestExecutionFinishRun += DATA.getTestId();
		}

		RequestBody body = RequestBody.create(JSON, gson.toJson(bodyObject));

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

		RequestBody body = RequestBody.create(JSON, gson.toJson(new TestRunFinishDTO()));

		Request request = new Request.Builder().url(endpointTestFinishRun).put(body)
				.addHeader("Authorization", "Bearer " + DATA.getAccessToken()).build();

		try {
			Response response = client.newCall(request).execute();

			response.body().close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void testScreenshotCollectionRequest(byte[] content, String timeData) {

		String endpointScreenshotCollection = endpoint
				.concat(FileUtils.readValueInProperties(endpointPath, "ENP_EXECUTION")).concat(DATA.getRunId())
				.concat("/tests/").concat(DATA.getTestId()).concat("/screenshots");

		final MediaType MEDIA_TYPE_HTTP = MediaType.parse("image/png");

		RequestBody body = RequestBody.create(MEDIA_TYPE_HTTP, content);

		Request request = null;

		if (timeData.isBlank()) {
			request = new Request.Builder().url(endpointScreenshotCollection).post(body)
					.addHeader("Authorization", "Bearer " + DATA.getAccessToken()).build();
		}

		else {
			request = new Request.Builder().url(endpointScreenshotCollection).post(body)
					.addHeader("Authorization", "Bearer " + DATA.getAccessToken())
					.addHeader("x-zbr-screenshot-captured-at", timeData).build();
		}

		try {

			Response response = client.newCall(request).execute();
			response.body().close();
		}

		catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

	}

	public void testExecutionLabelRequest(JsonObject labelItems) {
		String endpointTestRunLabels = endpoint.concat(FileUtils.readValueInProperties(endpointPath, "ENP_EXECUTION"))
				.concat(DATA.getRunId()).concat("/tests/").concat(DATA.getTestId()).concat("/labels");

		JsonArray labelsArray = labelItems.get("items").getAsJsonArray();
		ArrayList<LabelItemDTO> items = new ArrayList<>();

		for (JsonElement labels : labelsArray) {
			String cleaned = ResponseUtils.cleanString(labels.getAsJsonObject().toString());
			String[] labelsAux = cleaned.split(":");

			LabelItemDTO labelItem = new LabelItemDTO(labelsAux[0], labelsAux[1]);
			items.add(labelItem);
		}

		RequestBody body = RequestBody.create(JSON, gson.toJson(new TestRunLabelsDTO(items)));

		Request request = new Request.Builder().url(endpointTestRunLabels).put(body)
				.addHeader("Authorization", "Bearer " + DATA.getAccessToken()).build();

		try {
			Response response = client.newCall(request).execute();
			response.body().close();

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void sendLogs(Queue<LogDTO> endpointData) {
		String endpointLogs = endpoint.concat(FileUtils.readValueInProperties(endpointPath, "ENP_EXECUTION"))
				.concat(DATA.getRunId()).concat("/logs");

		RequestBody body = RequestBody.create(JSON, gson.toJson(endpointData));

		Request request = new Request.Builder().url(endpointLogs).post(body)
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
}
