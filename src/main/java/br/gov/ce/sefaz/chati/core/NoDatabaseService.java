package br.gov.ce.sefaz.chati.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gilmario
 * @param <T>
 * @param <K>
 */
public abstract class NoDatabaseService<T extends Entity<K>, K extends Serializable> {

    protected final Map<K, T> registros = new HashMap<>();

    public List<T> lista() {
        return registros.values().stream().toList();
    }

    public T update(T registro) {
        return save(registro);
    }

    public T save(T registro) {
        if (registros.containsKey(registro.getId())) {
            registros.replace(registro.getId(), registro);
        } else {
            registros.put(registro.getId(), registro);
        }
        return registro;
    }

    public T getByChave(K chave) {
        return registros.get(chave);
    }

    public void delete(K chave) {
        registros.remove(chave);
    }
}
