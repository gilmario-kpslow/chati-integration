package br.gov.ce.sefaz.chati.grupo;

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
    public List<Grupo> getLista() throws Exception {
        return service.lista();
    }

    @DELETE
    @Path("{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public void delete(@PathParam("id") String chave) {
        service.delete(chave);
    }

}
