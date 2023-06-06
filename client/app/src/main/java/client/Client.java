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
   
    /**
     * This creates a connection to the server.
     * @param host the hostname of the server to connect to
     * @param port the port number of the server to connect to
     * @throws IOException if the socket cannot be created
     */
    public Client(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        socket = new Socket(host, port);
        socket.setSoTimeout(1000);
    }

    /**
     * Make a ping request to the server to which the client is connected
     */
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
                // Throw an exception if the server response is null or not pong
                throw new RuntimeException("Invalid response");
            }
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());

            // Throw an exception if request cannot be sent to server or response cannot be read
            throw new RuntimeException("Error occurred: " + e.getMessage(), e);
        }
    }
}
