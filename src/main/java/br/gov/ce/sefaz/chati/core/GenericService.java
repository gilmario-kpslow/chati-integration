package br.gov.ce.sefaz.chati.core;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author gilmario
 * @param <P>
 * @param <K>
 */
public abstract class GenericService<P extends Serializable, K extends Entity<P>> {

    @ConfigProperty(name = "nodatabase")
    protected boolean noDatabase;

    protected abstract DatabaseService<P, K> getDatabaseService();

    protected abstract NoDatabaseService< K, P> getNoDatabaseService();

    public List<K> lista() throws Exception {
        if (noDatabase) {
            return getNoDatabaseService().lista();
        } else {
            return getDatabaseService().lista();
        }
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
        if (noDatabase) {
            return getNoDatabaseService().save(registro);
        }
        return getDatabaseService().save(registro);
    }

    K update(K registro) throws Exception {
        validarUpdate(registro);
        if (noDatabase) {
            return getNoDatabaseService().update(registro);
        }
        return getDatabaseService().update(registro);
    }

    public void delete(P id) {
        if (noDatabase) {
            getNoDatabaseService().delete(id);
        } else {
            getDatabaseService().delete(id);
        }
    }

}
