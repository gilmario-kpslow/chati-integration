package br.gov.ce.sefaz.chati.pocketbase.user;

import br.gov.ce.sefaz.chati.*;
import br.gov.ce.sefaz.chati.pocketbase.PageResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 *
 * @author gilmario
 */
@Path("user")
public class UserResource {

    @Inject
    UserService service;

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response cadastro(User chat) throws Exception {
        chat = service.saveOrUpdate(chat);
        Response r = new Response();
        r.setResposta(chat.getId());
        return r;
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public PageResponse<User> getLista() throws Exception {
        return service.lista();
    }

    @GET
    @Path("login")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public void login() throws Exception {
        service.login();
    }

    @GET
    @Path("getid/{chave}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public User getListaChat(@PathParam("chave") String chave) throws Exception {
        return service.getOne(chave);
    }

    @DELETE
    @Path("{chave}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public void delete(@PathParam("chave") String chave) throws Exception {
        service.delete(chave);
    }

}
