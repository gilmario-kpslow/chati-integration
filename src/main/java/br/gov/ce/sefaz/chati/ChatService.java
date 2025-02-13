package br.gov.ce.sefaz.chati;

import br.gov.ce.sefaz.chati.core.DatabaseService;
import br.gov.ce.sefaz.chati.core.GenericService;
import br.gov.ce.sefaz.chati.executor.google.GoogleExecutor;
import br.gov.ce.sefaz.chati.websocket.ChatSocket;
import br.gov.ce.sefaz.chati.websocket.Comando;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import java.io.IOException;
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
@RequestScoped
public class ChatService extends GenericService<ChatRegistro> {

    @Inject
    ChatDatabaseService databaseService;

    @Inject
    GoogleExecutor executor;

    @Inject
    ChatSocket chatSocket;

    @Override
    public ChatRegistro saveOrUpdate(ChatRegistro registro) throws Exception {
        var resp = super.saveOrUpdate(registro);
        chatSocket.broadcast(Comando.builder().comando("pesquisar").mensagem("Salvar ou atualizar").build());
        return resp;
    }

    @Override
    public void delete(String id) throws IOException, InterruptedException {
        super.delete(id);
        chatSocket.broadcast(Comando.builder().comando("pesquisar").mensagem("Deletar").build());
    }

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
        ChatRegistro dto = lista().getItems().stream().filter(a -> a.getChave().equals(chave)).findFirst().get();
        String mesagem = this.convertMessage(dto.getMensagem(), values);
        executor.executar(dto.getUrl(), mesagem);
        chatSocket.broadcast(Comando.builder().comando("pesquisar").mensagem("Executando notificação " + dto.getTitulo()).build());
    }

    @Override
    protected ChatRegistro save(ChatRegistro registro) throws Exception {
        registro.setChave(UUID.randomUUID().toString());
        return super.save(registro);
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
        ChatRegistro dto = getOne(chave);
        String mesagem = this.convertMessage(dto.getMensagem(), values);
        chatSocket.broadcast(Comando.builder().comando("mensagem").mensagem(mesagem).build());
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

    @Override
    protected DatabaseService<ChatRegistro> getDatabaseService() {
        return databaseService;
    }

}
