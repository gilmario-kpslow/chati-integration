package br.gov.ce.sefaz.chati.executor.google;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

/**
 *
 * @author gilmario
 */
@Path("")
public interface GoogleChatServiceInterface {

    @POST
    public void enviarMessagem(GoogleMensagemDTO msg);

}
