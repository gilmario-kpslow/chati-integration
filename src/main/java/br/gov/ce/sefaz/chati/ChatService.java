package br.gov.ce.sefaz.chati;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author gilmario
 */
@ApplicationScoped
public class ChatService {

    @ConfigProperty(name = "nodatabase")
    boolean noDatabase;

    @Inject
    DatabaseService databaseService;

    @Inject
    NoDatabaseService noDatabaseService;

    @Inject
    ChatExecutor executor;

    public List<ChatRegistro> lista() {
        if (noDatabase) {
            return noDatabaseService.lista();
        } else {
            return databaseService.lista();
        }
    }

    private void validar(ChatRegistro registro) {
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

    ChatRegistro saveOrUpdate(ChatRegistro registro) {
        if (Objects.isNull(registro.getId()) || registro.getId().isEmpty()) {
            return this.save(registro);
        }
        return this.update(registro);
    }

    ChatRegistro save(ChatRegistro registro) {
        registro.setId(UUID.randomUUID().toString());
        validar(registro);
        if (noDatabase) {
            return noDatabaseService.save(registro);
        }
        return databaseService.save(registro);
    }

    ChatRegistro update(ChatRegistro registro) {
        validar(registro);
        if (noDatabase) {
            return noDatabaseService.update(registro);
        }
        return databaseService.update(registro);
    }

    void delete(String chave) {
        if (noDatabase) {
            noDatabaseService.delete(chave);
        } else {
            databaseService.delete(chave);
        }
    }

    public ChatRegistro getByChave(String chave) {
        if (noDatabase) {
            return noDatabaseService.getByChave(chave);
        }
        return databaseService.getByChave(chave);
    }

}
