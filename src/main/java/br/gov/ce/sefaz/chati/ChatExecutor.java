package br.gov.ce.sefaz.chati;

import br.gov.ce.sefaz.chati.executor.google.GoogleChatServiceInterface;
import br.gov.ce.sefaz.chati.executor.google.GoogleChatServiceOption;
import br.gov.ce.sefaz.chati.executor.google.GoogleMensagemDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.net.URI;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 *
 * @author gilmario
 */
@ApplicationScoped
public class ChatExecutor {

    @ConfigProperty(name = "proxy.host")
    Optional<String> proxyHost;
    @ConfigProperty(name = "proxy.port")
    Optional<Integer> proxyPort;

    public void executar(String url, String mensagem) {
        RestClientBuilder builder = RestClientBuilder.newBuilder();
        if (proxyHost.isPresent() && proxyPort.isPresent()) {
            builder = builder.proxyAddress(proxyHost.get(), proxyPort.get());
        }

        GoogleChatServiceInterface service = builder
                .baseUri(URI.create(url))
                .register(GoogleChatServiceOption.class)
                .build(GoogleChatServiceInterface.class);

        service.enviarMessagem(new GoogleMensagemDTO(mensagem));

    }

}
