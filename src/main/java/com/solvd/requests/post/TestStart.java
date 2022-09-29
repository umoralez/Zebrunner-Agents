package com.solvd.requests.post;

import com.solvd.BaseClass;
import com.solvd.domain.TokenGenerationDTO;
import com.solvd.utils.FileUtils;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

public class TestStart extends BaseClass {
    public static void testStartRequest() {
        String projectKey = FileUtils.propertyValue("project-key").get("project-key");


//        String bodyJson = gson.toJson(bodyObject);
//
//        RequestBody body = RequestBody.create(JSON, bodyJson);
//        Request request = new Request.Builder().url(endpoint).post(body).build();
//
    }
}
