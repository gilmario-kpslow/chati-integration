package br.gov.ce.sefaz.chati.core;

import java.io.Serializable;

/**
 *
 * @author gilmario
 * @param <K>
 */
public abstract class Entity<K extends Serializable> {

    public abstract K getId();

    public abstract void setId(K id);

}
