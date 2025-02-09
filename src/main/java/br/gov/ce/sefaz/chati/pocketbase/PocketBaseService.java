package br.gov.ce.sefaz.chati.pocketbase;

import br.gov.ce.sefaz.chati.core.BaseEntidade;
import br.gov.ce.sefaz.chati.utils.JsonConverter;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.microprofile.config.inject.ConfigProperty;

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
//        RestClientBuilder builder = RestClientBuilder.newBuilder();
//        client = builder
//                .baseUri(URI.create(pocketbaseURL))
//                .build(PocketBaseClientInterface<T>.class);
    }

    public LoginResponse getLogin() {
        if (Objects.nonNull(loginResponse)) {
            return loginResponse;
        }
        loginResponse = client.login(new LoginRequest(pocketbaseUsuario, pocketbaseSenha));
        return loginResponse;
    }

    public <T extends BaseEntidade> PageResponse<T> listar(String entityName) {
        return client.listar(getToken(), entityName);
    }

    public <T extends BaseEntidade> T create(String entityName, T t) {
        try {
            System.out.println(JsonConverter.toJson(t));
            System.out.println(t.getClass().getName());
            System.out.println(t.getClass().getSuperclass().getName());
        } catch (IOException ex) {
            Logger.getLogger(PocketBaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (T) client.create(getToken(), entityName, t);
    }

    public <T extends BaseEntidade, K extends BaseEntidade> T update(String entityName, K t) {
        return (T) client.update(getToken(), entityName, t.getId(), t);
    }

    public void delete(String entityName, String id) {
        client.delete(getToken(), entityName, id);
    }

    public <T extends BaseEntidade> T getOne(String entityName, String id) {
        return (T) client.getOne(getToken(), entityName, id);
    }

    private String getToken() {
        return "Bearer " + getLogin().getToken();
    }

}
