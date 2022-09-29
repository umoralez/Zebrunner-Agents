package com.solvd;

import com.solvd.token.TokenGeneration;

public class EntryPoint {
    public static void main(String[] args) {
        TokenGeneration endpoint = new TokenGeneration();
        System.out.println(endpoint.tokenGeneration());
    }
}
