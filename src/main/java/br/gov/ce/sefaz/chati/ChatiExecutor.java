package br.gov.ce.sefaz.chati;

import br.gov.ce.sefaz.chati.executor.Executor;
import br.gov.ce.sefaz.chati.websocket.ChatSocket;
import br.gov.ce.sefaz.chati.websocket.Comando;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

/**
 *
 * @author gilmario
 */
@ApplicationScoped
public class ChatiExecutor implements Executor {
 
    public void init() {
        System.out.println("INIIIIITTTTT");
    }

    @Inject
    ChatSocket chatSocket;

    @Override
    public void notificar(ChatRegistro chati, String mensagem) {
        chatSocket.broadcast(Comando.builder().comando("mensagem").mensagem(mensagem).build());
    }

}
