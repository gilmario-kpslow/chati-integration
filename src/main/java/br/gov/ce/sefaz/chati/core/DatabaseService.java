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

    @Inject
    PocketBaseService pocketBaseService;

    protected abstract String getEntityName();

    protected abstract Class<T> getClassEntity();

    public PageResponse<T> listar() throws IOException, InterruptedException {
        return pocketBaseService.listar(getEntityName(), getClassEntity());

    }

    public T create(T t) throws IOException, InterruptedException {
        return pocketBaseService.create(getEntityName(), t, getClassEntity());
    }

    public T update(T entity) throws Exception {
        return this.pocketBaseService.update(getEntityName(), entity, getClassEntity(), entity.getId());
    }

    public void delete(String id) throws IOException, InterruptedException {
        pocketBaseService.delete(getEntityName(), id);
    }

    public T getOne(String id) throws IOException, InterruptedException {
        return pocketBaseService.getOne(getEntityName(), getClassEntity(), id);
    }

    protected PocketBaseService getPocketService() {
        return pocketBaseService;
    }
}
