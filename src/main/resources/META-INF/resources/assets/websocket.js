export class Socket {

    socket;

    connect(nome, open, messagem, error, close) {
        const prot = location.protocol === 'http:' ? 'ws:' : 'wss:';
        this.socket = new WebSocket(`${prot}//${location.host}/chat/${nome}`);
        this.socket.onopen = open;
        this.socket.onmessage = messagem;
        this.socket.onerror = error;
        this.socket.onclose = close;
    }

    send(mensagem) {
        this.socket.send(mensagem);
    }
}

