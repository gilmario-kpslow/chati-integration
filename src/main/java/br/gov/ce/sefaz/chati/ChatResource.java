package br.gov.ce.sefaz.chati;

import br.gov.ce.sefaz.chati.pocketbase.PageResponse;
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
    public PageResponse<ChatRegistro> getLista() throws Exception {
        return service.lista();
    }

    @GET
    @Path("getid/{chave}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public ChatRegistro getListaChat(@PathParam("chave") String chave) throws Exception {
        return service.getOne(chave);
    }

    @DELETE
    @Path("{chave}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public void delete(@PathParam("chave") String chave) throws Exception {
        service.delete(chave);
    }

    @GET
    @Path("executar/{chave}")
    public void executar(@PathParam("chave") String chave, @Context UriInfo uriInfo) throws Exception {
        MultivaluedMap<String, String> values = uriInfo.getQueryParameters();
        service.execute(chave, values);
    }

    @GET
    @Path("notificar/{id}")
    public void notificar(@PathParam("id") String chave, @Context UriInfo uriInfo) throws Exception {
        service.notificar(chave, uriInfo.getQueryParameters());
    }

    @GET
    @Path("ativar/{id}")
    public void ativar(@PathParam("id") String id) throws Exception {
        service.ativar(id);
        System.out.println("OK");
    }

}
