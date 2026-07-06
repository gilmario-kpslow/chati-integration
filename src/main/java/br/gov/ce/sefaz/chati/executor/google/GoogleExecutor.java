package br.gov.ce.sefaz.chati.executor.google;

import br.gov.ce.sefaz.chati.ChatRegistro;
import br.gov.ce.sefaz.chati.executor.Executor;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
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
public class GoogleExecutor implements Executor {

    @ConfigProperty(name = "proxy.host")
    Optional<String> proxyHost;
    @ConfigProperty(name = "proxy.port")
    Optional<Integer> proxyPort;
    @ConfigProperty(name = "google.url")
    private String googleUrl;

    GoogleChatServiceInterface service;

    @PostConstruct
    public void init() {
        System.out.println("br.gov.ce.sefaz.chati.executor.google.GoogleExecutor.init()");
        RestClientBuilder builder = RestClientBuilder.newBuilder();
        if (proxyHost.isPresent() && proxyPort.isPresent()) {
            builder = builder.proxyAddress(proxyHost.get(), proxyPort.get());
        }

        service = builder
                .baseUri(URI.create(googleUrl))
                .register(GoogleChatServiceOption.class)
                .build(GoogleChatServiceInterface.class);
    }

    @Override
    public void notificar(ChatRegistro chati, String mensagem) {
        service.enviarMessagem(resolveURL(chati.getUrl()), new GoogleMensagemDTO(mensagem));
    }

    private String resolveURL(String req) {
        return req.replaceAll(googleUrl, "");
    }

    @PreDestroy
    private void close() {
        System.out.println("br.gov.ce.sefaz.chati.executor.google.GoogleExecutor.close()");
    }

}
