package br.gov.ce.sefaz.chati;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.UriInfo;
import java.util.List;

/**
 *
 * @author gilmario
 */
@Path("registro")
public class ChatResource {

    @Inject
    ChatService service;

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response cadastro(ChatRegistro chat) throws Exception {
        chat = service.saveOrUpdate(chat);
        Response r = new Response();
        r.setResposta(chat.getId());
        return r;
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public List<ChatRegistro> getLista() throws Exception {
        return service.lista();
    }

    @GET
    @Path("getid/{chave}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public ChatRegistro getListaChat(@PathParam("chave") String chave) throws Exception {
        return service.getByChave(chave);
    }

    @DELETE
    @Path("{chave}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public void delete(@PathParam("chave") String chave) {
        service.delete(chave);
    }

    @GET
    @Path("executar/{chave}")
    public void executar(@PathParam("chave") String chave, @Context UriInfo uriInfo) throws Exception {
        MultivaluedMap<String, String> values = uriInfo.getQueryParameters();
        service.execute(chave, values);
    }

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Path("restore")
    public void restore(List<ChatRegistro> lista) throws Exception {
        service.restoreBackup(lista);
    }
}
