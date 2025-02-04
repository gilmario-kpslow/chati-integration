package br.gov.ce.sefaz.chati.pocketbase;

import br.gov.ce.sefaz.chati.core.Entity;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import java.net.URI;
import java.util.Objects;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 *
 * @author gilmario
 */
@Singleton
public class PocketBaseService {

    @ConfigProperty(name = "app.pocketbase.url")
    String pocketbaseURL;
    @ConfigProperty(name = "app.pocketbase.username")
    private String pocketbaseUsuario;
    @ConfigProperty(name = "app.pocketbase.password")
    private String pocketbaseSenha;
    PocketBaseClientInterface client;
    private LoginResponse loginResponse;

    @PostConstruct
    protected void init() {
        RestClientBuilder builder = RestClientBuilder.newBuilder();
        client = builder
                .baseUri(URI.create(pocketbaseURL))
                .build(PocketBaseClientInterface.class);
    }

    public LoginResponse getLogin() {
        if (Objects.nonNull(loginResponse)) {
            return loginResponse;
        }
        loginResponse = client.login(new LoginRequest(pocketbaseUsuario, pocketbaseSenha));
        return loginResponse;
    }

    public <T extends Entity> PageResponse<T> listar(String entityName) {
        return client.listar(getToken(), entityName);
    }

    public <T extends Entity> T create(String entityName, T t) {
        return client.create(getToken(), entityName, t);
    }

    public <T extends Entity> T update(String entityName, T t) {
        return client.update(getToken(), entityName, t.getId(), t);
    }

    public void delete(String entityName, String id) {
        client.delete(getToken(), entityName, id);
    }

    public <T extends Entity> T getOne(String entityName, String id) {
        return client.getOne(getToken(), entityName, id);
    }

    private String getToken() {
        return "Bearer " + getLogin().getToken();
    }

}
