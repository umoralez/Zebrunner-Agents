package com.solvd.requests.post;

import java.io.IOException;

import com.solvd.BaseClass;
import com.solvd.domain.TestExecutionDTO;
import com.solvd.utils.FileUtils;
import com.solvd.utils.RequestUpdate;
import com.solvd.utils.ResponseUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class TestExcecutionStart extends BaseClass{
    
    private StringBuilder endpointTestStart = new StringBuilder(properties.getProperty("URL"));

    public void testExcecutionStartRequest(){
        String projectId = FileUtils.readValueInProperties(idPath, "id").get("id") + "/tests";

        endpointTestStart.append(RequestUpdate.addQueryParamsValues("ENP_RUN_FINISH", projectId));
    
        String name = "Testing A";
        String className = "com.solvd.DemoTest";
        String methodName = "fooTest()";
        TestExecutionDTO bodyObject = new TestExecutionDTO(name, className, methodName);
        String bodyJson = gson.toJson(bodyObject);

        RequestBody body = RequestBody.create(JSON, bodyJson);

        String keyToken = "Authorization";
        Request request = new Request.Builder().url(endpointTestStart.toString()).post(body).addHeader(keyToken, FileUtils.readValueInProperties(tokenPath, "Authorization=Bearer ").get(keyToken)).build();
        LOGGER.info(request.urlString());

        try  {
            Response response = client.newCall(request).execute();
            String bodyResponse = response.body().string();
            
            response.body().close();
            LOGGER.info(bodyResponse);

            FileUtils.addValueProperties(ResponseUtils.splitResponse(bodyResponse, "\"id\""), idPath, "test_id=");
            
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
