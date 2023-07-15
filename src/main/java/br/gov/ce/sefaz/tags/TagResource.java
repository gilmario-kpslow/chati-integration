package br.gov.ce.sefaz.tags;

import br.gov.ce.sefaz.chati.*;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author gilmario
 */
@Path("tag")
public class TagResource {

    @Inject
    private TagService service;

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response cadastro(Tag chat) {
        chat = service.saveOrUpdate(chat);
        Response r = new Response();
        r.setResposta(chat.getId());
        return r;
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public List<Tag> getLista() {
        return service.lista();
    }

    @DELETE
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public void delete(@PathParam("id") String chave) {
        service.delete(chave);
    }

}
