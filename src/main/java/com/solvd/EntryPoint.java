package com.solvd;

import com.solvd.requests.post.TestStart;
import com.solvd.requests.post.TokenGeneration;

import java.io.IOException;

public class EntryPoint {
    public static void main(String[] args) throws IOException {
        TokenGeneration endpoint = new TokenGeneration();
        endpoint.tokenGeneration();

        TestStart endpointTS = new TestStart();
        endpointTS.testStartRequest();
    }


}
