package server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    ThreadPoolExecutor executor;

    public Server() {
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
    }

    public void startServer() {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());

                // Thread clientThread = new Thread(() -> handleClient(clientSocket));
                this.executor.submit(() -> handleClient(clientSocket));
                // clientThread.start();
            }
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());

        }

    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String request;
            while ((request = reader.readLine()) != null) {
                if (request.equals("ping")) {
                    System.out.println("Recieved ping from:" + clientSocket.getInetAddress());
                    writer.println("pong");
                } else {
                    System.out.println("Invalid request : " + clientSocket.getInetAddress());
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Exception: " + clientSocket.getInetAddress() + " " + e.getMessage());

        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Exception closing : " + clientSocket.getInetAddress() + " " + e.getMessage());

            }

        }
    }

}
