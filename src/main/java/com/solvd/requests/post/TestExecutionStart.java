package com.solvd.requests.post;

import com.google.gson.JsonObject;
import com.solvd.BaseClass;
import com.solvd.domain.TestExecutionStartDTO;
import com.solvd.utils.FileUtils;
import com.solvd.utils.RequestUpdate;
import com.solvd.utils.ResponseUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class TestExecutionStart extends BaseClass {

    public void testExecutionStart(JsonObject endpointData) {

        endpointTestStart.append(RequestUpdate.completeEndpoint("ENP_EXECUTION", "/tests"));

        TestExecutionStartDTO bodyObject = new TestExecutionStartDTO(
                endpointData.get("name").getAsString(),
                endpointData.get("className").getAsString(),
                endpointData.get("methodName").getAsString());

        String bodyJson = gson.toJson(bodyObject);

        RequestBody body = RequestBody.create(JSON, bodyJson);

        String keyToken = "Authorization";
        Request request = new Request.Builder().url(endpointTestStart.toString())
                .post(body).addHeader(keyToken, FileUtils.readValueInProperties(tokenPath, "Authorization=Bearer "))
                .build();
                LOGGER.debug("ExeStart"+endpointTestStart.toString());
        try  {
            Response response = client.newCall(request).execute();
            String bodyResponse = response.body().string();

            response.body().close();
            System.out.println(bodyResponse);

            //FileUtils.addValueProperties(ResponseUtils.splitResponse(bodyResponse, "\"id\""), idPath, "test_id=");
            FileUtils.changeProperties(ResponseUtils.splitResponse(bodyResponse, "\"id\""), "test_id", idPath);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}