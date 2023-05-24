package br.gov.ce.sefaz;

import java.net.URI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 *
 * @author gilmario
 */
@Path("")
public class MessagemResource {

    private final RestClient restClient;

    public MessagemResource() {
        restClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create(Constantes.URL_CHAT)).proxyAddress("http://10.10.10.10", 9090).build(RestClient.class);
    }

    @Path("chat")
    @GET
    public String sendMensagem(@QueryParam("mensagem") String mensagem) {
        return restClient.mensagem(new Mensagem(mensagem));
    }

}
