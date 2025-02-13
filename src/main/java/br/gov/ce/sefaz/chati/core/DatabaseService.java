package br.gov.ce.sefaz.chati.core;

import br.gov.ce.sefaz.chati.pocketbase.PageResponse;
import br.gov.ce.sefaz.chati.pocketbase.PocketBaseService;
import jakarta.inject.Inject;
import java.io.IOException;

/**
 *
 * @author gilmario
 * @param <T>
 */
public abstract class DatabaseService<T extends BaseEntidade> {

    protected final String entityName;

    @Inject
    PocketBaseService pocketBaseService;

    public DatabaseService(String entity) {
        this.entityName = entity;
    }

    protected abstract Class<T> getClassEntity();

    public PageResponse<T> listar() throws IOException, InterruptedException {
        return pocketBaseService.listar(entityName, getClassEntity());

    }

    public T create(T t) throws IOException, InterruptedException {
        return pocketBaseService.create(entityName, t, getClassEntity());
    }

    public T update(T entity) throws Exception {
        return this.pocketBaseService.update(entityName, entity, getClassEntity(), entity.getId());
    }

    public void delete(String id) throws IOException, InterruptedException {
        pocketBaseService.delete(entityName, id);
    }

    public T getOne(String id) throws IOException, InterruptedException {
        return pocketBaseService.getOne(entityName, getClassEntity(), id);
    }

    protected PocketBaseService getPocketService() {
        return pocketBaseService;
    }
}
