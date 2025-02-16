package br.gov.ce.sefaz.chati.executor.google;

import br.gov.ce.sefaz.chati.ChatRegistro;
import br.gov.ce.sefaz.chati.executor.Executor;
import jakarta.enterprise.context.RequestScoped;
import java.net.URI;
import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 *
 * @author gilmario
 */
@RequestScoped
public class GoogleExecutor implements Executor {

    @ConfigProperty(name = "proxy.host")
    Optional<String> proxyHost;
    @ConfigProperty(name = "proxy.port")
    Optional<Integer> proxyPort;

    @Override
    public void notificar(ChatRegistro chati, String mensagem) {
        RestClientBuilder builder = RestClientBuilder.newBuilder();
        if (proxyHost.isPresent() && proxyPort.isPresent()) {
            builder = builder.proxyAddress(proxyHost.get(), proxyPort.get());
        }

        GoogleChatServiceInterface service = builder
                .baseUri(URI.create(chati.getUrl()))
                .register(GoogleChatServiceOption.class)
                .build(GoogleChatServiceInterface.class);

        service.enviarMessagem(new GoogleMensagemDTO(mensagem));

    }

}
