package br.gov.ce.sefaz.chati;

import br.gov.ce.sefaz.chati.core.DatabaseService;
import br.gov.ce.sefaz.chati.core.GenericService;
import br.gov.ce.sefaz.chati.executor.ExecutorService;
import br.gov.ce.sefaz.chati.websocket.ChatSocket;
import br.gov.ce.sefaz.chati.websocket.Comando;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author gilmario
 */
@RequestScoped
public class ChatService extends GenericService<ChatRegistro> {

    @Inject
    ChatDatabaseService databaseService;

    @Inject
    ExecutorService executor;

    @Inject
    ChatSocket chatSocket;

    @Inject
    ChatiExecutor chatiExecutor;

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
//        if (!registro.getUrl().toLowerCase().startsWith("https://")) {
//            throw new RuntimeException("A URL e deve iniciar com https://");
//        }
    }

    public void execute(String chave, MultivaluedMap<String, String> values) throws Exception {
        ChatRegistro registro = lista().getItems().stream().filter(a -> a.getChave().equals(chave)).findFirst().get();
        executor.notifica(registro, values);
        chatSocket.broadcast(Comando.builder().comando("mensagem").mensagem("Executando notificação " + registro.getTitulo()).build());
    }

    @Override
    protected ChatRegistro save(ChatRegistro registro) throws Exception {
        registro.setChave(UUID.randomUUID().toString());
        registro.setAtivo(Boolean.TRUE);
        return super.save(registro);
    }

    public void notificar(String chave, MultivaluedMap<String, String> values) throws Exception {
        ChatRegistro dto = getOne(chave);
        executor.notifica(dto, values, chatiExecutor);
    }

    @Override
    protected DatabaseService<ChatRegistro> getDatabaseService() {
        return databaseService;
    }

    public void ativar(String id) throws Exception {
        ChatRegistro dto = getOne(id);
        dto.setAtivo(!dto.getAtivo());
        databaseService.update(dto);
    }

}
