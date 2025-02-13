package br.gov.ce.sefaz.chati.websocket;

import br.gov.ce.sefaz.chati.utils.JsonConverter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilmario
 */
@ServerEndpoint(value = "/chat/{username}")
@ApplicationScoped
public class ChatSocket {

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        broadcast(Comando.builder().comando("mensagem").mensagem("Conectado").build());
        sessions.put(username, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessions.remove(username);
        broadcast(Comando.builder().comando("mensagem").mensagem("Usuario desconectado").build());
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        sessions.remove(username);
        broadcast(Comando.builder().comando("error").mensagem("Error" + throwable.getMessage()).build());
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        broadcast(Comando.builder().comando("mensagem").mensagem(message).build());
    }

    public void broadcast(Comando message) {
        sessions.values().forEach((Session s) -> {
            try {
                s.getAsyncRemote().sendObject(JsonConverter.toJson(message), (result) -> {
                    if (result.getException() != null) {
                        System.out.println("Unable to send message: " + result.getException());
                    }
                });
            } catch (IOException ex) {
                Logger.getLogger(ChatSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
