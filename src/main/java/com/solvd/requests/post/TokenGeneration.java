package com.solvd.requests.post;

import com.solvd.BaseClass;
import com.solvd.domain.TokenGenerationDTO;
import com.solvd.utils.FileUtils;
import com.solvd.utils.SplitResponse;
import com.squareup.okhttp.*;

import java.io.IOException;

public class TokenGeneration extends BaseClass {
    private final String endpoint = properties.getProperty("URL") + properties.getProperty("ENP_TOKEN_GENERATE");

    public void tokenGeneration() {
        String token = FileUtils.propertyValue("access-token").get("access-token");
        TokenGenerationDTO bodyObject = new TokenGenerationDTO(token);

        String bodyJson = gson.toJson(bodyObject);

        RequestBody body = RequestBody.create(JSON, bodyJson);
        Request request = new Request.Builder().url(endpoint).post(body).build();

        try  {
            Response response = client.newCall(request).execute();
            String bodyResponse = response.body().string();

            response.body().close();

           FileUtils.tokenProperties(SplitResponse.splitToken(bodyResponse));


        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

    }

}
