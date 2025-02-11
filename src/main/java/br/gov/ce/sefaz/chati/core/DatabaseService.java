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

    private final String entityName;

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

//    public T update(T t) {
//        return client.update(getToken(), entityName, t.getId(), t);
//    }
//
//    public void delete(String id) {
//        client.delete(getToken(), entityName, id);
//    }
//
//    public T getOne(String id) {
//        return client.getOne(getToken(), entityName, id);
//    }
//
//    private String getToken() {
//        return "Bearer " + getLogin().getToken();
//    }
//    public PageResponse<T> lista() throws Exception {
//        return pocketBaseService.listar(entityName);
//    }
//
//    public T save(T entity) throws Exception {
//        return pocketBaseService.create(entityName, entity);
//    }
//
    public T update(T entity) throws Exception {
        return this.pocketBaseService.update(entityName, entity, getClassEntity(), entity.getId());
    }
//
//    public void delete(String id) {
//        pocketBaseService.delete(entityName, id);
//    }
//

    public T getOne(String id) {
//        return pocketBaseService.getOne(entityName, id);
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
