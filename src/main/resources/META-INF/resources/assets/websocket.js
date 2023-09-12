export class Socket {

    socket;

    connect(nome, open, messagem, error, close) {
        this.socket = new WebSocket("ws://" + location.host + "/chat/" + nome);
        this.socket.onopen = open;
        this.socket.onmessage = messagem;
        this.socket.onerror = error;
        this.socket.onclose = close;
    }

    send(mensagem) {
        this.socket.send(mensagem);
    }
}

