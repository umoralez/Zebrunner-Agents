package com.solvd.requests.post;

import com.solvd.BaseClass;
import com.solvd.domain.TestStartDTO;
import com.solvd.utils.DateFormatter;
import com.solvd.utils.FileUtils;
import com.solvd.utils.RequestUpdate;
import com.solvd.utils.ResponseUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static com.solvd.utils.FileUtils.removeInitialSpaces;

public class TestStart extends BaseClass {
    private final String projectKey = FileUtils.propertyValue("projectKey").get("projectKey");
    private StringBuilder endpointTestStart = new StringBuilder(properties.getProperty("URL"));


    public void testStartRequest() {
        String name = "Test name";
        String framework = "testng";
        String projectKey = removeInitialSpaces(FileUtils.propertyValue("project-key").get("project-key"));

        endpointTestStart.append(RequestUpdate.addQueryParamsValues("ENP_RUN_START", projectKey));

        String startedAt = DateFormatter.getCurrentTime();

        TestStartDTO bodyObject = new TestStartDTO(name, framework, startedAt);
        String bodyJson = gson.toJson(bodyObject);

        RequestBody body = RequestBody.create(JSON, bodyJson);

        String keyToken = "Authorization";
        Request request = new Request.Builder().url(endpointTestStart.toString()).post(body).addHeader(keyToken, FileUtils.readValueInProperties(tokenPath, "Authorization=Bearer ").get(keyToken)).build();

        try  {
            Response response = client.newCall(request).execute();
            String bodyResponse = response.body().string();

            response.body().close();


            FileUtils.addValueProperties(ResponseUtils.splitResponse(bodyResponse, "\"id\""), idPath, "id=");

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
