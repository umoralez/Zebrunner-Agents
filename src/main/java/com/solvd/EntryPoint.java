package com.solvd;

import com.solvd.requests.post.TokenGeneration;

public class EntryPoint {
    public static void main(String[] args) {
        TokenGeneration endpoint = new TokenGeneration();
        endpoint.tokenGeneration();
    }
}
