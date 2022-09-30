package com.solvd.utils;

import java.util.Map;

public class SplitResponse {
    public static String splitToken(String responseJson) {
        String[] splitToken = responseJson.split(":");
        String token= "";
        for (int i = 0; i < splitToken.length; i++) {
            //System.out.println("responseJson = " + splitToken[i]);
            if (splitToken[i].contains("\"authToken\"")) {
                //System.out.println(splitToken[i + 1].split(",")[0]);
                token = splitToken[i + 1].split(",")[0];
            }
        }
        return token.replace("\"", "");
    }
}
