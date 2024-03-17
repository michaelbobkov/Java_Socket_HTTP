package com.mycompany.app.http;

import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpExample {
    public static void main(String[] args) throws IOException, InterruptedException {
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://www.google.com")).GET().build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

    }
}
