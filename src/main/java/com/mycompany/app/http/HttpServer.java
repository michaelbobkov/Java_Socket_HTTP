package com.mycompany.app.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    public final int port;
    public ExecutorService executorService;

    public HttpServer(int port, int poolsize) {
        this.port = port;
        executorService = Executors.newFixedThreadPool(poolsize);
    }

    public void run(){

        try {
            ServerSocket serversocket = new ServerSocket(port);
            while (true){
                var socket = serversocket.accept();
                System.out.println("Socket accepted");
                executorService.submit(()->processSocket(socket));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processSocket(Socket socket) {
        try (socket;
                var inputStream = new DataInputStream(socket.getInputStream());
             var outputStream = new DataOutputStream(socket.getOutputStream())){
            Thread.sleep(10000);
            System.out.println(new String(inputStream.readNBytes(400)));
            byte[] body = Files.readAllBytes(Path.of("src/main/resources/example.html"));
            outputStream.write("""
                    HTTP/1.1 200 OK
                    content-type: text/html
                    content-length: %s
                    """.formatted(body.length).getBytes());
            outputStream.write(System.lineSeparator().getBytes());
            outputStream.write(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
