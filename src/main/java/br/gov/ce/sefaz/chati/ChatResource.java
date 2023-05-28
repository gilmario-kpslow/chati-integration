package br.gov.ce.sefaz.chati;

import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author gilmario
 */
@Path("registro")
public class ChatResource {

    @Inject
    private ChatService service;

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response cadastro(ChatRegistro chat) {
        Response r = new Response();
        r.setResposta(UUID.randomUUID().toString());
        chat.setId(r.getResposta());
        service.save(chat);
        return r;
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public List<ChatRegistro> getLista() {
        return service.lista();
    }

    @GET
    @Path("getid/{chave}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public ChatRegistro getListaChat(@PathParam("chave") String chave) {
        return service.getByChave(chave);
    }

    @GET
    @Path("executar/{chave}")
    public void executar(@PathParam("chave") String chave, @Context UriInfo uriInfo) {
        System.out.println(uriInfo.getQueryParameters());

        MultivaluedMap<String, String> values = uriInfo.getQueryParameters();
        service.execute(chave, values);
    }
}
