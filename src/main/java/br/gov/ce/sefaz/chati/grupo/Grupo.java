package br.gov.ce.sefaz.chati.grupo;

import br.gov.ce.sefaz.chati.core.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author gilmario
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Grupo extends Entity {

    private String nome;
    private String cor;

}
