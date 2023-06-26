package br.gov.ce.sefaz.chati;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Singleton;

/**
 *
 * @author gilmario
 */
@Singleton
public class NoDatabaseService {

    private final Map<String, ChatRegistro> registros = new HashMap<>();

    List<ChatRegistro> lista() {
        return registros.values().stream().toList();
    }

    ChatRegistro update(ChatRegistro registro) {
        return save(registro);
    }

    ChatRegistro save(ChatRegistro registro) {
        if (registros.containsKey(registro.getId())) {
            registros.replace(registro.getId(), registro);
        } else {
            registros.put(registro.getId(), registro);
        }
        return registro;
    }

    ChatRegistro getByChave(String chave) {
        return registros.get(chave);
    }

    void delete(String chave) {
        registros.remove(chave);
    }

}
