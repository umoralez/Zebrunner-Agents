package com.solvd.requests.post;

import com.solvd.BaseClass;
import com.solvd.domain.TestStartDTO;
import com.solvd.domain.TokenGenerationDTO;
import com.solvd.utils.FileUtils;
import com.solvd.utils.SplitResponse;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

public class TestStart extends BaseClass {
    private final String projectKey = FileUtils.propertyValue("projectKey").get("projectKey");
    private final String endpointTestStart = properties.getProperty("URL") + properties.getProperty("ENP_RUN_START");


    public void testStartRequest() {
        FileUtils.readToken();

        TestStartDTO bodyObject = new TestStartDTO();

        String bodyJson = gson.toJson(bodyObject);
        RequestBody body = RequestBody.create(JSON, bodyJson);

        Request request = new Request.Builder().url(endpointTestStart).post(body).build();

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
