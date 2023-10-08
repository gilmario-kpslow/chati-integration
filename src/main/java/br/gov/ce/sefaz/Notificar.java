package br.gov.ce.sefaz;

import br.gov.ce.sefaz.chati.ChatService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;

/**
 *
 * @author gilmario
 */
@Path("")
public class Notificar {

    @Inject
    ChatService service;

    @GET
    @Path("notificar/{chave}")
    public void executar(@PathParam("chave") String chave, @Context UriInfo uriInfo) throws Exception {
        MultivaluedMap<String, String> values = uriInfo.getQueryParameters();
        service.notificar(chave, values);
    }
}
