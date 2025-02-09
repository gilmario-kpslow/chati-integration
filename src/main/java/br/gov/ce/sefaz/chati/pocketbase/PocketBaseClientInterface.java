package br.gov.ce.sefaz.chati.pocketbase;

import br.gov.ce.sefaz.chati.core.BaseEntidade;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

/**
 *
 * @author gilmario
 * @param <T>
 */
@Path("/api/collections")
public interface PocketBaseClientInterface<T extends BaseEntidade> {

    @POST
    @Path("_superusers/auth-with-password")
    public LoginResponse login(LoginRequest request);

    @GET
    @Path("{collection}/records")
    public PageResponse<T> listar(@HeaderParam("Authorization") String token, @PathParam("collection") String stream);

    @POST
    @Path("{collection}/records")
    public <T extends BaseEntidade> T create(@HeaderParam("Authorization") String token, @PathParam("collection") String stream, T t);

    @PATCH
    @Path("{collection}/records/{id}")
    public <T extends BaseEntidade, K extends BaseEntidade> T update(@HeaderParam("Authorization") String token, @PathParam("collection") String stream, @PathParam("id") String id, K t);

    @DELETE
    @Path("{collection}/records/{id}")
    public <T extends BaseEntidade> T delete(@HeaderParam("Authorization") String token, @PathParam("collection") String stream, @PathParam("id") String id);

    @GET
    @Path("{collection}/records/{id}")
    public <T extends BaseEntidade> T getOne(@HeaderParam("Authorization") String token, @PathParam("collection") String entityName, @PathParam("id") String id);
}
