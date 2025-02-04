package br.gov.ce.sefaz.chati.pocketbase;

import br.gov.ce.sefaz.chati.core.Entity;
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
 */
@Path("/api/collections")
public interface PocketBaseClientInterface {

    @POST
    @Path("_superusers/auth-with-password")
    public LoginResponse login(LoginRequest request);

    @GET
    @Path("{collection}/records")
    public <T extends Entity> PageResponse<T> listar(@HeaderParam("Authorization") String token, @PathParam("collection") String stream);

    @POST
    @Path("{collection}/records")
    public <T extends Entity> T create(@HeaderParam("Authorization") String token, @PathParam("collection") String stream, T t);

    @PATCH
    @Path("{collection}/records/{id}")
    public <T extends Entity> T update(@HeaderParam("Authorization") String token, @PathParam("collection") String stream, @PathParam("id") String id, T t);

    @DELETE
    @Path("{collection}/records/{id}")
    public <T extends Entity> T delete(@HeaderParam("Authorization") String token, @PathParam("collection") String stream, @PathParam("id") String id);

    @GET
    @Path("{collection}/records/{id}")
    public <T extends Entity> T getOne(@HeaderParam("Authorization") String token, @PathParam("collection") String entityName, @PathParam("id") String id);
}
