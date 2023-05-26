package br.gov.ce.sefaz.chati;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author gilmario
 */
@Singleton
public class ChatService {

    private static List<ChatRegistroDTO> lista = new ArrayList<>();

    @Inject
    ChatExecutor executor;

    public void save(ChatRegistroDTO registro) {
        validar(registro);
        lista.add(registro);
    }

    public List<ChatRegistroDTO> getLista() {
        return lista;
    }

    private void validar(ChatRegistroDTO registro) {
        if (Objects.isNull(registro)) {
            throw new RuntimeException("Dados inválidos");
        }

        if (Objects.isNull(registro.getTitulo()) || registro.getTitulo().isBlank()) {
            throw new RuntimeException("Titulo inválidos");
        }
    }

    public void execute(String chave) {
        ChatRegistroDTO dto = getByChave(chave);
        executor.executar(dto);
    }

    public ChatRegistroDTO getByChave(String chave) {
        return getLista().stream().filter(p -> p.getId().equals(chave)).findFirst().orElseThrow(() -> new RuntimeException("Chave não encontrada"));
    }
}