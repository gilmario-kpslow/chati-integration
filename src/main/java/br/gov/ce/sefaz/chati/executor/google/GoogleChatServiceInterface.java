package br.gov.ce.sefaz.chati.executor.google;

import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

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
