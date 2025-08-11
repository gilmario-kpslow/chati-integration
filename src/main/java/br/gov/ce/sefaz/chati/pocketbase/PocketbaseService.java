package br.gov.ce.sefaz.chati.pocketbase;

import br.com.pocketbase.PocketbaseLibProvider;
import br.com.pocketbase.http.PocketbaseConfiguracao;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.util.Optional;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author gilmario
 */
@ApplicationScoped
@Getter
public class PocketbaseService {

    @ConfigProperty(name = "app.pocketbase.url")
    String pocketbaseURL;
    @ConfigProperty(name = "app.pocketbase.username")
    private String pocketbaseUsuario;
    @ConfigProperty(name = "app.pocketbase.password")
    private String pocketbaseSenha;
    @ConfigProperty(name = "proxy.host")
    Optional<String> proxyHost;
    @ConfigProperty(name = "proxy.port")
    Optional<Integer> proxyPort;

    private PocketbaseLibProvider provider;

    @PostConstruct
    private void init() {
        HttpClient client;
        if (proxyHost.isPresent() && proxyPort.isPresent()) {
            client = HttpClient.newBuilder().proxy(ProxySelector.of(new InetSocketAddress(proxyHost.get(), proxyPort.get()))).build();
        } else {
            client = HttpClient.newHttpClient();
        }

        PocketbaseConfiguracao configuracao = new PocketbaseConfiguracao();
        configuracao.setServerURL(pocketbaseURL);
        configuracao.setUsuario(pocketbaseUsuario);
        configuracao.setSenha(pocketbaseSenha);

        provider = new PocketbaseLibProvider(configuracao, client);
    }

}
