package edu.whoi.publisher;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;

/**
 * Hello world!
 *
 */
public class App 
{
    static Socket socket;

    public static void main(final String[] args) {
        IO.Options opts = new IO.Options();
        opts.query = "token=" + args[1];

        try {
            socket = IO.socket(args[0], opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        // PAY ATTENTION!
        // Blocking in an event handler can cause the whole socket to
        // get jammed up. This was a painful lesson!

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Connected to server!");
            }
        });
        socket.connect();

        // Create the heartbeat thread that emits events
        Heartbeat heartbeat = new Heartbeat(socket);
        heartbeat.start();
        try {
            heartbeat.join();
        } catch (final InterruptedException e) {
            return;
        }
    }
}
