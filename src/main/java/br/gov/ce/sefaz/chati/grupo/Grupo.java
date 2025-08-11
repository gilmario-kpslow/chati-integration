package br.gov.ce.sefaz.chati.grupo;

import br.com.pocketbase.registro.Registro;
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
public class Grupo extends Registro {

    private String nome;
    private String cor;

}
