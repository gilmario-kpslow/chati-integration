/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    public List<K> lista() {
        if (noDatabase) {
            return getNoDatabaseService().lista();
        } else {
            return getDatabaseService().lista();
        }
    }

    protected abstract void validar(K registro);

    public K saveOrUpdate(K registro) {
        if (Objects.isNull(registro.getId())) {
            return this.save(registro);
        }
        return this.update(registro);
    }

    protected K save(K registro) {
        validar(registro);
        if (noDatabase) {
            return getNoDatabaseService().save(registro);
        }
        return getDatabaseService().save(registro);
    }

    K update(K registro) {
        validar(registro);
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
