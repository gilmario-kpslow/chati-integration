package br.gov.ce.sefaz.chati.versao;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.ResourceBundle;

/**
 *
 * @author gilmario
 */
@Path(value = "versao")
public class VersaoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Versao versao() {
        ResourceBundle bundle = ResourceBundle.getBundle("api_info");
        String[] dataHora = bundle.getString("DATA_BUILD").split(" ");
        return Versao.builder()
                .data(dataHora[0])
                .versao(bundle.getString("VERSAO"))
                .projeto(bundle.getString("PROJETO")).build();
    }
}
