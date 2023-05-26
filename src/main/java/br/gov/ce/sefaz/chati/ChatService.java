package br.gov.ce.sefaz.chati;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.MultivaluedMap;

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

    public void execute(String chave, MultivaluedMap<String, String> values) {
        ChatRegistroDTO dto = getByChave(chave);

//        StringBuilder mensagemFormatada = new StringBuilder(dto.getMensagem());
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

    public ChatRegistroDTO getByChave(String chave) {
        return getLista().stream().filter(p -> p.getId().equals(chave)).findFirst().orElseThrow(() -> new RuntimeException("Chave não encontrada"));
    }
}
