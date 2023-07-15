package br.gov.ce.sefaz.chati;

import br.gov.ce.sefaz.chati.core.GenericService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;

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
    ChatExecutor executor;

    @Override
    protected void validar(ChatRegistro registro) {
        if (Objects.isNull(registro)) {
            throw new RuntimeException("Dados inválidos");
        }

        if (Objects.isNull(registro.getTitulo()) || registro.getTitulo().isBlank()) {
            throw new RuntimeException("Titulo inválidos");
        }
    }

    public void execute(String chave, MultivaluedMap<String, String> values) {
        ChatRegistro dto = getByChave(chave);

        String mesagem = dto.getMensagem();
        if (Objects.nonNull(values)) {
            for (Map.Entry<String, List<String>> entry : values.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                mesagem = mesagem.replaceAll("\\$\\{" + key + "\\}", value.get(0));
            }
        }
        System.out.println(mesagem);
        executor.executar(dto.getUrl(), mesagem);
    }

    @Override
    protected ChatRegistro save(ChatRegistro registro) {
        registro.setId(UUID.randomUUID().toString());
        return super.save(registro);
    }

    public ChatRegistro getByChave(String chave) {
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

}
