package br.gov.ce.sefaz.chati.versao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author gilmario
 */
@Path(value = "status, versao")
public class VersaoResource {

    @GET
    public String versao() {
        return "1.0.3";
    }
}
