/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package server;

import java.util.Arrays;

public class App {

    public static void main(String[] args) {

        System.out.println("args "+ Arrays.toString(args));

        String port = args[0];

        Server server = new Server(Integer.parseInt(port));
        server.startServer();
    }
}
