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
    //
    ThreadPoolExecutor executor;
    int port;

    /**
     * This creates an instance of a server that listens on a port on localhost
     * 
     * @param port is the port that the server listens on localhost
     */
    public Server(int port) {
        /**
         * Create a threadpool for serving client requests. Each client connection
         * will be served by a dedicated thread. Due to this, the server will be limited
         * to only serving the same number of clients as there are threads in the
         * threadpool
         */
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        this.port = port;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                // establish a TCP connection with client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());

                // submit task to threadpool to serve this client's requests
                this.executor.submit(() -> handleClient(clientSocket));
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
