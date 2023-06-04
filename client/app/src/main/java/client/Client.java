package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
    private Socket socket;
    private String host;
    private int port;

    public Client() {
        this.host = "localhost";
        this.port = 8080;
        try {
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + host);
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    public void run() {
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
                System.out.println("Invalid response");
            }
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}
