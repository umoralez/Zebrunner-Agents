package com.solvd.requests.put;

import java.io.IOException;

import com.solvd.BaseClass;
import com.solvd.domain.TestRunFinishDTO;
import com.solvd.utils.DateFormatter;
import com.solvd.utils.FileUtils;
import com.solvd.utils.RequestUpdate;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class TestRunFinish extends BaseClass{
    private StringBuilder endpointTestFinishRun = new StringBuilder(properties.getProperty("URL"));

    public void testRunFinishRequest(){
        String projectId = FileUtils.readValueInProperties(idPath, "id").get("id");
        endpointTestFinishRun.append(RequestUpdate.addQueryParamsValues("ENP_RUN_FINISH", projectId));

        String endedAt = DateFormatter.getCurrentTime();
        TestRunFinishDTO bodyObject = new TestRunFinishDTO(endedAt);
        String bodyJson = gson.toJson(bodyObject);

        RequestBody body = RequestBody.create(JSON, bodyJson);

        String keyToken = "Authorization";
        Request request = new Request.Builder().url(endpointTestFinishRun.toString()).put(body).addHeader(keyToken, FileUtils.readValueInProperties(tokenPath, "Authorization=Bearer ").get(keyToken)).build();
        LOGGER.info(request.urlString());
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