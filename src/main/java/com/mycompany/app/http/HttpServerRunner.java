package com.mycompany.app.http;

public class HttpServerRunner {
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer(8082, 100);
        httpServer.run();
    }
}
