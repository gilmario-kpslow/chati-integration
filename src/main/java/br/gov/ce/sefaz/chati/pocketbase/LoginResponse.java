package br.gov.ce.sefaz.chati.pocketbase;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author gilmario
 */
@Getter
@Setter
@ToString
public class LoginResponse {

    private Superusuario record;
    private String token;

}
