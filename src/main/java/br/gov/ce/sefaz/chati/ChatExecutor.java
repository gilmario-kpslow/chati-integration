/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gov.ce.sefaz.chati;

import java.net.URI;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 *
 * @author gilmario
 */
@ApplicationScoped
public class ChatExecutor {

    public void executar(ChatRegistroDTO dto) {

        GoogleChatServiceInterface service = RestClientBuilder.newBuilder()
                .baseUri(URI.create(dto.getUrl()))
                .register(GoogleChatServiceOption.class)
                .build(GoogleChatServiceInterface.class);

        service.enviarMessagem(new GoogleMensagemDTO(dto.getMensagem()));

    }

}
