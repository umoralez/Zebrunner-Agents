package com.solvd.requests.post;

import com.solvd.BaseClass;
import com.solvd.domain.TestStartDTO;
import com.solvd.domain.TokenGenerationDTO;
import com.solvd.utils.DateFormatter;
import com.solvd.utils.FileUtils;
import com.solvd.utils.SplitResponse;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestStart extends BaseClass {
    private final String projectKey = FileUtils.propertyValue("projectKey").get("projectKey");
    private final String endpointTestStart = properties.getProperty("URL") + properties.getProperty("ENP_RUN_START");


    public void testStartRequest() {
        //ring projectKey, String name, String framework, String startedAt
        //String projectKey = FileUtils.propertyValue("project-key").get("project-key");
        String name = "Test name";
        String framework = "testng";
        //TODO: add project key from agent file (FileUtils.propertyValue) add query param in ENP_RUN_START (config.properties) in the endpoint before request call.
        String startedAt = DateFormatter.getCurrentTime();

        System.out.println(startedAt);
        TestStartDTO bodyObject = new TestStartDTO(name, framework, startedAt);
        String bodyJson = gson.toJson(bodyObject);
        RequestBody body = RequestBody.create(JSON, bodyJson);


        String keyToken = "Authorization";
        Request request = new Request.Builder().url(endpointTestStart).post(body).addHeader(keyToken, FileUtils.readToken().get(keyToken)).build();

        try  {
            Response response = client.newCall(request).execute();
            String bodyResponse = response.body().string();

            response.body().close();
            System.out.println(bodyResponse);

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
