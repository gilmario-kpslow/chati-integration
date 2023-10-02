package br.gov.ce.sefaz.chati.core;

import java.io.Serializable;
import lombok.EqualsAndHashCode;

/**
 *
 * @author gilmario
 * @param <K>
 */
@EqualsAndHashCode()
public abstract class Entity<K extends Serializable> {

    public abstract K getId();

    public abstract void setId(K id);

}
