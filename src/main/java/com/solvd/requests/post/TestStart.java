package com.solvd.requests.post;

import com.google.gson.JsonObject;
import com.solvd.BaseClass;
import com.solvd.domain.TestStartDTO;
import com.solvd.utils.FileUtils;
import com.solvd.utils.RequestUpdate;
import com.solvd.utils.ResponseUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static com.solvd.utils.FileUtils.removeInitialSpaces;

public class TestStart extends BaseClass {

    public void testStartRequest(JsonObject endpointData) {

        String projectKey = removeInitialSpaces(FileUtils.propertyValue("project-key").get("project-key"));

        endpointTestStart.append(RequestUpdate.addQueryParamsValue("ENP_RUN_START", projectKey));

        TestStartDTO bodyObject = new TestStartDTO(
            endpointData.get("name").getAsString(),
            endpointData.get("framework").getAsString());
        String bodyJson = gson.toJson(bodyObject);

        RequestBody body = RequestBody.create(JSON, bodyJson);

        String keyToken = "Authorization";
        Request request = new Request.Builder().url(endpointTestStart.toString())
                .post(body).addHeader(keyToken, FileUtils.readValueInProperties(tokenPath, "Authorization=Bearer "))
                .build();
        LOGGER.debug("TestStart"+endpointTestStart.toString());
        try  {
            Response response = client.newCall(request).execute();
            String bodyResponse = response.body().string();

            response.body().close();


            //FileUtils.addValueProperties(ResponseUtils.splitResponse(bodyResponse, "\"id\""), idPath, "id=");
            FileUtils.changeProperties(ResponseUtils.splitResponse(bodyResponse, "\"id\""), "id", idPath);
            
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
