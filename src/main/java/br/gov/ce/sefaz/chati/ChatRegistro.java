package br.gov.ce.sefaz.chati;

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
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ChatRegistro extends Entity<String> {

    private String titulo;
    private String mensagem;
    private String url;
    private String id;
    private String cor;
    private String assunto;

}
