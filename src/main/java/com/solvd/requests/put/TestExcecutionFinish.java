package com.solvd.requests.put;

import java.io.IOException;
import com.solvd.BaseClass;
import com.solvd.domain.TestExcecutionFinishDTO;
import com.solvd.utils.FileUtils;
import com.solvd.utils.RequestUpdate;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class TestExcecutionFinish extends BaseClass{
    private StringBuilder endpointTestFinishRun = new StringBuilder(properties.getProperty("URL"));

    //{{id}}/tests/{1341}
    public void testExcecutionFinishRequest(){
        String projectId = FileUtils.readValueInProperties(idPath, "id").get("id") + "/tests" + FileUtils.readValueInProperties(idPath, "test_id=");
        endpointTestFinishRun.append(RequestUpdate.addQueryParamsValues("ENP_EXCECUTION_FINISH", projectId));

        String result = "PASSED";
        TestExcecutionFinishDTO bodyObject = new TestExcecutionFinishDTO(result);
        String bodyJson = gson.toJson(bodyObject);

        RequestBody body = RequestBody.create(JSON, bodyJson);

        String keyToken = "Authorization";
        Request request = new Request.Builder().url(endpointTestFinishRun.toString()).put(body).addHeader(keyToken, FileUtils.readValueInProperties(tokenPath, "Authorization=Bearer ").get(keyToken)).build();
        
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
