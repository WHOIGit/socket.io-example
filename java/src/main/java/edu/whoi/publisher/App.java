package edu.whoi.publisher;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import org.json.JSONObject;

// just for this demo
import java.time.format.DateTimeFormatter;
import java.time.ZonedDateTime;


/**
 * Hello world!
 *
 */
public class App 
{
    static Socket socket;

    public static void update() {
        JSONObject obj = new JSONObject();
        obj.put("time", ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME));

        System.out.println("Publishing update " + socket.connected());
        socket.emit("update", obj); // obj is automatically serialized
    }

    public static void main(final String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format",
        "[%1$tF %1$tT] [%4$-7s] %5$s %n");

        IO.Options opts = new IO.Options();
        opts.query = "token=" + args[1];

        try {
            socket = IO.socket(args[0], opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                // for demo purposes: send an update every tick
                while (true) {
                    update();

                    try {
                        Thread.sleep(1000);
                    } catch (final InterruptedException e) {
                        break;
                    }
                }
            }
        });

        socket.on(Socket.EVENT_ERROR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Error!");
            }
        });

        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Disconnected from server!");
            }
        });

        socket.connect();
    }
}
