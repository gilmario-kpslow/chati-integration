package br.gov.ce.sefaz.chati.executor;

import br.gov.ce.sefaz.chati.ChatRegistro;
import br.gov.ce.sefaz.chati.ChatiExecutor;
import static br.gov.ce.sefaz.chati.executor.ProvidersEnum.CHATI;
import static br.gov.ce.sefaz.chati.executor.ProvidersEnum.GOOGLE;
import br.gov.ce.sefaz.chati.executor.google.GoogleExecutor;
import br.gov.ce.sefaz.chati.websocket.ChatSocket;
import br.gov.ce.sefaz.chati.websocket.Comando;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author gilmario
 */
@RequestScoped
public class ExecutorService {

    @Inject
    ChatiExecutor chatiExecutor;

    @Inject
    GoogleExecutor googleExecutor;

    @Inject
    ChatSocket chatSocket;

    public void notifica(ChatRegistro registro, MultivaluedMap<String, String> values) throws Exception {
        if (!registro.getAtivo()) {
            throw new Exception("O Registro esta inativo");
        }
        ProvidersEnum p = ProvidersEnum.valueOfName(registro.getProvider().toUpperCase());

        switch (p) {
            case CHATI ->
                notifica(registro, values, chatiExecutor);
            case GOOGLE ->
                notifica(registro, values, googleExecutor);
            default ->
                chatSocket.broadcast(Comando.builder().comando("Nenhum provider para esse registro: ".concat(registro.getTitulo()).concat(" - Provider: ").concat(registro.getProvider())).build());
        }

    }

    public void notifica(ChatRegistro registro, MultivaluedMap<String, String> values, Executor executor) {

        String mensagem = this.convertMessage(registro.getMensagem(), values);
        executor.notificar(registro, mensagem);

    }

    private String convertMessage(String mensagem, MultivaluedMap<String, String> values) {
        String novaMesagem = mensagem;
        if (Objects.nonNull(values)) {
            for (Map.Entry<String, List<String>> entry : values.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                novaMesagem = novaMesagem.replaceAll("\\$\\{" + key + "\\}", value.get(0));
            }
        }

        return novaMesagem;
    }
}
