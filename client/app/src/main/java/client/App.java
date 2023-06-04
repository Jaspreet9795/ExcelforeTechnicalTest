/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) {
        Client client = new Client();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> scheduledFuture = executor.scheduleAtFixedRate(client, 0, 1, TimeUnit.SECONDS);
    }
}