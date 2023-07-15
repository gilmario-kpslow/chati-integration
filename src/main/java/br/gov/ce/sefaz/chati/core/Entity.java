/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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