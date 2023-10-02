package br.gov.ce.sefaz.chati.versao;

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
public class Versao {

    private String versao;
    private String data;
//    private String hora;
    private String projeto;

}
