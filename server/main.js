const server = require('http').createServer();
const io = require('socket.io')(server);

io.on('connection', (client) => {
    let type = 'Subscriber';

    // Simple authentication
    if (client.handshake.query.token === process.env.TOKEN) {
        type = 'Publisher';
    }

    // We only allow the publisher to broadcast updates
    if (type === 'Publisher') {
        client.on('update', (data) => {
            console.log('Broadcasting update from publisher');
            client.broadcast.emit('update', data);
        });
    }

    console.log(type + ' connected');
    client.on('disconnect', (reason) => {
        console.log(type + ' disconnected (' + reason + ')');
    });
});

server.listen(process.env.PORT || 9041);
