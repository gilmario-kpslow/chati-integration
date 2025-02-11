package br.gov.ce.sefaz.chati.core;

import br.gov.ce.sefaz.chati.pocketbase.PageResponse;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author gilmario
 * @param <K>
 */
public abstract class GenericService<K extends BaseEntidade> {

    protected abstract DatabaseService<K> getDatabaseService();

    public PageResponse<K> lista() throws Exception {
        return getDatabaseService().listar();
    }

    protected void validarSave(K registro) {
    }

    protected void validarUpdate(K registro) {
    }

    protected void validar(K registro) {
    }

    public K saveOrUpdate(K registro) throws Exception {
        this.validar(registro);
        if (Objects.isNull(registro.getId())) {
            return this.save(registro);
        }
        return this.update(registro);
    }

    protected K save(K registro) throws Exception {
        validarSave(registro);
        return getDatabaseService().create(registro);
    }

    K update(K registro) throws Exception {
        validarUpdate(registro);
        return getDatabaseService().update(registro);
    }

    public void delete(String id) throws IOException, InterruptedException {
        getDatabaseService().delete(id);
    }

    public K getOne(String id) throws IOException, InterruptedException {
        return getDatabaseService().getOne(id);
    }

}
