package br.gov.ce.sefaz.chati.websocket;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author gilmario
 */
@Getter
@Setter
@Builder
public class Comando {

    private String mensagem;
    private String comando;
    private String parametros;
}
