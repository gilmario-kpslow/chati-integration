package br.gov.ce.sefaz.chati.executor.google;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.annotations.jaxrs.HeaderParam;

/**
 *
 * @author gilmario
 */
@Path("")
public interface GoogleChatServiceInterface {

    @POST
    @Path("{req}")
    public void enviarMessagem(@HeaderParam("req") String req, GoogleMensagemDTO msg);

}
