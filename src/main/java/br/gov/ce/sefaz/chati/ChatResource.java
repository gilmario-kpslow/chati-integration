package br.gov.ce.sefaz.chati;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
