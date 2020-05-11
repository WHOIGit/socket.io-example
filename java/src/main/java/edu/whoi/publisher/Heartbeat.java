package edu.whoi.publisher;

import io.socket.client.Socket;
import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;
import org.json.JSONObject;


public class Heartbeat extends Thread {
    private Socket socket;

    public Heartbeat(Socket socket) {
        this.socket = socket;
    }

    private void update() {
        JSONObject obj = new JSONObject();
        obj.put("time", ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME));

        System.out.println("Publishing update");
        socket.emit("update", obj); // obj is automatically serialized
    }
    
    public void run() {
        try {
            while (true) {
                update();
                Thread.sleep(1000);
            }
        } catch (final InterruptedException e) {
            return;
        }
    }
}
