package br.gov.ce.sefaz.chati;

import br.gov.ce.sefaz.chati.core.BaseEntidade;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author gilmario
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
public class ChatRegistro extends BaseEntidade {

    private String titulo;
    private String mensagem;
    private String url;
    private String cor;
    private String chave;

}
