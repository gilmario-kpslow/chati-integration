package br.gov.ce.sefaz.chati;

import br.gov.ce.sefaz.chati.executor.GoogleChatServiceInterface;
import br.gov.ce.sefaz.chati.executor.GoogleChatServiceOption;
import br.gov.ce.sefaz.chati.executor.GoogleMensagemDTO;
import java.net.URI;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 *
 * @author gilmario
 */
@ApplicationScoped
public class ChatExecutor {

    final static String proxyUrl = System.getenv("PROXY_HOST");
    final static String port = System.getenv("PROXY_PORT");

    public void executar(String url, String mensagem) {

        RestClientBuilder builder = RestClientBuilder.newBuilder();
        if (Objects.nonNull(proxyUrl) && Objects.nonNull(port)) {
            builder = builder.proxyAddress(proxyUrl, Integer.parseInt(port));
        }

        GoogleChatServiceInterface service = builder
                .baseUri(URI.create(url))
                .register(GoogleChatServiceOption.class)
                .build(GoogleChatServiceInterface.class);

        service.enviarMessagem(new GoogleMensagemDTO(mensagem));

    }

}
