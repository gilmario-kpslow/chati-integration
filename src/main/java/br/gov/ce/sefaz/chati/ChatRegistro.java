package br.gov.ce.sefaz.chati;

import br.com.pocketbase.registro.Registro;
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
public class ChatRegistro extends Registro {

    private String titulo;
    private String mensagem;
    private String cor;
    private String chave;
    private String url;
    private Boolean ativo;
    private String provider;

}
