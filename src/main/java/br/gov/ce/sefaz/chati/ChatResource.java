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
import javax.ws.rs.core.MediaType;

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
    public Response cadastro(ChatRegistroDTO chat) {
        Response r = new Response();
        r.setResposta(UUID.randomUUID().toString());
        chat.setId(r.getResposta());
        service.save(chat);
        return r;
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public List<ChatRegistroDTO> getLista() {
        return service.getLista();
    }

    @GET
    @Path("getid/{chave}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public ChatRegistroDTO getListaChat(@PathParam("chave") String chave) {
        System.out.println(chave);
        return service.getLista().get(0);
    }

    @GET
    @Path("executar/{chave}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public void executar(@PathParam("chave") String chave) {
        System.out.println(chave);
//        return service.getLista().get(0);
    }
}
