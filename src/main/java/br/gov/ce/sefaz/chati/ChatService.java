package br.gov.ce.sefaz.chati;

import br.com.pocketbase.http.ParametrosRequestBuilder;
import br.gov.ce.sefaz.chati.executor.ExecutorService;
import br.gov.ce.sefaz.chati.pocketbase.PocketbaseService;
import br.gov.ce.sefaz.chati.websocket.ChatSocket;
import br.gov.ce.sefaz.chati.websocket.Comando;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author gilmario
 */
@Dependent
public class ChatService {

    @Inject
    ExecutorService executor;

    @Inject
    ChatSocket chatSocket;

    @Inject
    ChatiExecutor chatiExecutor;

    @Inject
    PocketbaseService pocketbaseService;

    private ChatPocketbaseService service;

    @ConfigProperty(name = "app.pocketbase.collection")
    Optional<String> colecao;

    @PostConstruct
    private void init() throws Exception {
        service = pocketbaseService.getProvider().getServiceInstance(ChatPocketbaseService.class, colecao.get());
    }

    public ChatRegistro criarAtualizar(ChatRegistro registro) throws Exception {
        if (Objects.isNull(registro.getId())) {
            registro.setChave(UUID.randomUUID().toString());
            registro.setAtivo(Boolean.TRUE);
        }
        ChatRegistro resp = service.criarAtualizar(registro);
        chatSocket.broadcast(Comando.builder().comando("pesquisar").mensagem("Salvar ou atualizar").build());
        return resp;
    }

    public void deletar(ChatRegistro t) throws Exception {
        service.deletar(t);
        chatSocket.broadcast(Comando.builder().comando("pesquisar").mensagem("Deletar").build());
    }

    public void execute(String chave, MultivaluedMap<String, String> values) throws Exception {
        ChatRegistro registro = buscarPorChave(chave);
        executor.notifica(registro, values);
        chatSocket.broadcast(Comando.builder().comando("mensagem").mensagem("Executando notificação " + registro.getTitulo()).build());
    }

    public void notificar(String chave, MultivaluedMap<String, String> values) throws Exception {
        ChatRegistro dto = buscarPorChave(chave);
        executor.notifica(dto, values, chatiExecutor);
    }

    public void ativar(String id) throws Exception {
        ChatRegistro dto = service.buscarPorId(id);
        dto.setAtivo(!dto.getAtivo());
        service.atualizar(dto);
    }

    public ChatRegistro buscarPorChave(String chave) throws Exception {
        Optional<ChatRegistro> op = service.paginar(new ParametrosRequestBuilder().filter("chave='" + chave + "'").build()).getItems().stream().findFirst();
        if (op.isPresent()) {
            return op.get();
        }
        return null;
    }

    public List<ChatRegistro> listarTodos() throws Exception {
        return service.listarTodos();
    }

}
