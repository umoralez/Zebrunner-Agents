package com.solvd.token;

import com.google.gson.Gson;
import com.solvd.BaseClass;
import com.solvd.domain.TokenGenerationDTO;
import com.solvd.utils.FileUtils;
import com.squareup.okhttp.*;

import java.io.IOException;

public class TokenGeneration extends BaseClass {
    private final String endpoint = properties.getProperty("URL") + properties.getProperty("ENP_TOKEN_GENERATE");

    public  String tokenGeneration() {
        String token = FileUtils.propertyValue(propertiesFile,"access-token").get("access-token");
        TokenGenerationDTO bodyObject = new TokenGenerationDTO(token);

        String bodyJson = gson.toJson(bodyObject);

        RequestBody body = RequestBody.create(JSON, bodyJson);
        Request request = new Request.Builder().url(endpoint).post(body).build();

        try  {
            Response response = client.newCall(request).execute();
            String bodyResponse = response.body().string();

            //response.body().close();

            return bodyResponse;

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return e.getMessage();
        }
    }

}
