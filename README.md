# Socket.IO Example

This repository contains a small example of using [Socket.IO][]. It includes

[Socket.IO]: https://Socket.IO

    * a Java publisher node that emits a heartbeat message every second;
    * a browser-side JavaScript client that subscribes to the heartbeat messages;
    * a Node.js server that routes messages between them; and
    * and example of running these services under Docker.

This example code was put together hastily and may not be the finest example of Socket.IO code. And there are indications that development of Socket.IO may be discontinued.

Please refer to the Socket.IO documentation 

## Lessons Learned

### There is no built-in authentication

To prevent a malicious browser nodes from injecting its own heartbeat events, we need to authenticate the publisher. This is done through use of a `?token=` GET parameter which should be kept secret between the publisher and server.

### Do not block in a Java event handler

The initial version of the Java publisher node looked like this:

```java
socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
    @Override
    public void call(Object... args) {
        while (true) {
            socket.emit("update", "...");
            Thread.sleep(1000);
        }
    }
});
```

This led to a lengthy, headache-inducing debugging session to figure out why a single heartbeat was emitted and then nothing afterwards, with no errors. This was ultimately due to the fact taht this event handler was blocking the thread that was simultaneously used to enqueue outgoing messages.


## Lessons Not Learned

### Using HTTPS

No attempt was made to encrypt communication with the server.

Note that the Socket.IO endpoints are prefixed `/socket.io/` so it should be straightforward to set up a reverse proxy for these paths on an existing HTTPS server.
