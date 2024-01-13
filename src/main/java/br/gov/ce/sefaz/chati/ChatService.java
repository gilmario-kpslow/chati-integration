package br.gov.ce.sefaz.chati;

import br.gov.ce.sefaz.chati.executor.google.GoogleExecutor;
import br.gov.ce.sefaz.chati.core.GenericService;
import br.gov.ce.sefaz.chati.websocket.ChatSocket;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gilmario
 */
@ApplicationScoped
public class ChatService extends GenericService<String, ChatRegistro> {

    @Inject
    ChatDatabaseService databaseService;

    @Inject
    NoDatabaseChatRegistroService noDatabaseService;

    @Inject
    GoogleExecutor executor;

    @Inject
    ChatSocket chatSocket;

    @Override
    protected void validar(ChatRegistro registro) {
        if (Objects.isNull(registro)) {
            throw new RuntimeException("Dados inválidos");
        }

        if (Objects.nonNull(registro.getId()) && registro.getId().isBlank()) {
            registro.setId(null);
        }

        if (Objects.isNull(registro.getTitulo()) || registro.getTitulo().isBlank()) {
            throw new RuntimeException("Titulo inválidos");
        }

        if (Objects.isNull(registro.getUrl())) {
            throw new RuntimeException("Dados inválidos");
        }
        if (!registro.getUrl().toLowerCase().startsWith("https://")) {
            throw new RuntimeException("A URL e deve iniciar com https://");
        }
    }

    public void execute(String chave, MultivaluedMap<String, String> values) throws Exception {
        ChatRegistro dto = getByChave(chave);
        String mesagem = this.convertMessage(dto.getMensagem(), values);
        chatSocket.broadcast("Executando notificação!" + dto.getTitulo());
        executor.executar(dto.getUrl(), mesagem);
    }

    @Override
    protected ChatRegistro save(ChatRegistro registro) throws Exception {
        chatSocket.broadcast("Registro Salvo!");
        registro.setId(UUID.randomUUID().toString());
        if (Objects.isNull(registro.getCor())) {
            registro.setCor("#003399");
        }

        return super.save(registro);
    }

    public ChatRegistro getByChave(String chave) throws Exception {
        if (noDatabase) {
            return noDatabaseService.getByChave(chave);
        }
        return databaseService.getByChave(chave);
    }

    @Override
    protected ChatDatabaseService getDatabaseService() {
        return this.databaseService;
    }

    @Override
    protected NoDatabaseChatRegistroService getNoDatabaseService() {
        return this.noDatabaseService;
    }

    public void restoreBackup(List<ChatRegistro> lista) {
        lista.stream().forEach(registro -> {
            try {
                super.save(registro);
            } catch (Exception ex) {
                Logger.getLogger(ChatService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void notificar(String chave, MultivaluedMap<String, String> values) throws Exception {
        ChatRegistro dto = getByChave(chave);
        String mesagem = this.convertMessage(dto.getMensagem(), values);
        chatSocket.broadcast(mesagem);
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
