package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private String host;
    private int port;

    public Client(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        socket = new Socket(host, port);
        socket.setSoTimeout(1000);
    }

    public void makePingRequest() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            PrintWriter writer = new PrintWriter(this.socket.getOutputStream(), true);

            System.out.println("Connected to " + this.host + ":" + this.port);

            writer.println("ping");
            System.out.println("Ping sent");

            String response = reader.readLine();
            if (response != null && response.equals("pong")) {
                System.out.println("Received pong");
            } else {
                throw new RuntimeException("Invalid response");
            }
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
            throw new RuntimeException("Error occurred: " + e.getMessage(), e);
        }
    }
}
