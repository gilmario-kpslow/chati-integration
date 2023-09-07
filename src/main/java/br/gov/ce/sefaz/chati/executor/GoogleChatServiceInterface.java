package br.gov.ce.sefaz.chati.executor;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author gilmario
 */
@Path("")
public interface GoogleChatServiceInterface {

    @POST
    public void enviarMessagem(GoogleMensagemDTO msg);

}
