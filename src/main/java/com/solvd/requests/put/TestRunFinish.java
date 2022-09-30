package com.solvd.requests.put;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.solvd.BaseClass;
import com.solvd.domain.TestRunFinishDTO;
import com.solvd.utils.FileUtils;
import com.solvd.utils.RequestUpdate;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class TestRunFinish extends BaseClass{
    private final StringBuilder endpointTestFinishRun = new StringBuilder(properties.getProperty("URL"));

    public void testRunFinishRequest(JsonObject endExecution){
        String projectId = FileUtils.readValueInProperties(idPath, "id");
        endpointTestFinishRun.append(RequestUpdate.addQueryParamsValue("ENP_RUN_FINISH", projectId));

        TestRunFinishDTO bodyObject = new TestRunFinishDTO(endExecution.get("endedAt").toString());
        String bodyJson = gson.toJson(bodyObject);

        RequestBody body = RequestBody.create(JSON, bodyJson);

        String keyToken = "Authorization";
        Request request = new Request.Builder().url(endpointTestFinishRun.toString())
                .put(body).addHeader(keyToken, FileUtils.readValueInProperties(tokenPath, "Authorization=Bearer "))
                .build();
        LOGGER.debug("TestFinish"+endpointTestFinishRun.toString());
        try  {
            Response response = client.newCall(request).execute();
            String bodyResponse = response.body().string();
            
            response.body().close();
            LOGGER.info(bodyResponse);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
