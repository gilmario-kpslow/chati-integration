package br.gov.ce.sefaz.chati.grupo;

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
@Path("grupo")
public class GrupoResource {

    @Inject
    GrupoService service;

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response cadastro(Grupo entity) throws Exception {
        entity = service.saveOrUpdate(entity);
        Response r = new Response();
        r.setResposta(entity.getId());
        return r;
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public PageResponse<Grupo> getLista() throws Exception {
        return service.lista();
    }

    @DELETE
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public void delete(@PathParam("id") String chave) throws Exception {
        service.delete(chave);
    }

}
