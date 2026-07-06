package br.gov.ce.sefaz.chati;

import br.gov.ce.sefaz.kafka.MensagemProducer;
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

    @Inject
    MensagemProducer producer;

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response cadastro(ChatRegistro chat) throws Exception {
        chat = service.criarAtualizar(chat);
        Response r = new Response();
        r.setResposta(chat.getId());
        return r;
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public List<ChatRegistro> getLista() throws Exception {
        return service.listarTodos();
    }

    @GET
    @Path("getid/{chave}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public ChatRegistro getListaChat(@PathParam("chave") String chave) throws Exception {
        return service.buscarPorChave(chave);
    }

    @DELETE
//    @Path("")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public void delete(ChatRegistro chat) throws Exception {
        service.deletar(chat);
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
//        System.out.println("OK");
    }

    @GET
    @Path("kafka")
    public void testeKafka() throws Exception {
        producer.publicaMensagem("Mensagem estranha teste.");
    }

}
