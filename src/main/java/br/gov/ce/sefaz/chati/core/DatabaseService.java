package br.gov.ce.sefaz.chati.core;

import br.gov.ce.sefaz.chati.pocketbase.PageResponse;
import br.gov.ce.sefaz.chati.pocketbase.PocketBaseService;
import jakarta.inject.Inject;

/**
 *
 * @author gilmario
 * @param <K>
 */
public abstract class DatabaseService<K extends Entity> {

    private final String entityName;

    @Inject
    PocketBaseService pocketBaseService;

    public DatabaseService(String entity) {
        this.entityName = entity;
    }

    public PageResponse<K> lista() throws Exception {
        return pocketBaseService.listar(entityName);
    }

    public K save(K entity) throws Exception {
        return pocketBaseService.create(entityName, entity);
    }

    public K update(K entity) throws Exception {
        return pocketBaseService.update(entityName, entity);
    }

    public void delete(String id) {
        pocketBaseService.delete(entityName, id);
    }

    public K getOne(String id) {
        return pocketBaseService.getOne(entityName, id);
    }
}
