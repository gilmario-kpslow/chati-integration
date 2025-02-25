package br.gov.ce.sefaz.chati.pocketbase;

import br.gov.ce.sefaz.chati.core.BaseEntidade;
import br.gov.ce.sefaz.chati.pocketbase.user.LoginUserResponse;
import br.gov.ce.sefaz.chati.utils.JsonConverter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.server.ExportException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author gilmario
 */
@ApplicationScoped
@Getter
public class PocketBaseService {

    private static final String BASE_URL = "/api/collections/";
    @ConfigProperty(name = "app.pocketbase.url")
    String pocketbaseURL;
    @ConfigProperty(name = "app.pocketbase.username")
    private String pocketbaseUsuario;
    @ConfigProperty(name = "app.pocketbase.password")
    private String pocketbaseSenha;
    private LoginUserResponse loginResponse;
    private HttpClient client;

    @ConfigProperty(name = "proxy.host")
    Optional<String> proxyHost;
    @ConfigProperty(name = "proxy.port")
    Optional<Integer> proxyPort;

    public LoginUserResponse getLogin() throws IOException, InterruptedException {
        if (Objects.nonNull(loginResponse)) {
            return loginResponse;
        }
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .uri(URI.create(new StringBuilder(pocketbaseURL).append(BASE_URL).append("_pb_users_auth_/auth-with-password").toString()))
                .POST(HttpRequest.BodyPublishers.ofString(JsonConverter.toJson(new LoginRequest(pocketbaseUsuario, pocketbaseSenha))))
                .build();

        HttpResponse<String> response = getClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new ExportException(response.body());
        }

        loginResponse = JsonConverter.fromJson(response.body(), LoginUserResponse.class);

        return loginResponse;
    }
//    public LoginResponse getLogin() throws IOException, InterruptedException {
//        if (Objects.nonNull(loginResponse)) {
//            return loginResponse;
//        }
//        LOG.info("Efetuando login");
//        HttpRequest request = HttpRequest
//                .newBuilder()
//                .header("Content-Type", MediaType.APPLICATION_JSON)
//                .header("Accept", MediaType.APPLICATION_JSON)
//                .uri(URI.create(new StringBuilder(pocketbaseURL).append(BASE_URL).append("_superusers/auth-with-password").toString()))
//                .POST(HttpRequest.BodyPublishers.ofString(JsonConverter.toJson(new LoginRequest(pocketbaseUsuario, pocketbaseSenha))))
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        loginResponse = JsonConverter.fromJson(response.body(), LoginResponse.class);
//
//        return loginResponse;
//    }
    private static final Logger LOG = Logger.getLogger(PocketBaseService.class.getName());

    public <T extends BaseEntidade> PageResponse<T> listar(String entityName, Class<T> classResponse) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .uri(URI.create(new StringBuilder(pocketbaseURL).append(BASE_URL).append(entityName).append("/records").toString()))
                .header("Authorization", getToken())
                .GET()
                .build();

        HttpResponse<String> response = getClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new ExportException(response.body());
        }

        return JsonConverter.fromJsonPage(response.body(), classResponse);
    }

    public <T extends BaseEntidade> T create(String entityName, T t, Class<T> classResponse) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .uri(URI.create(new StringBuilder(pocketbaseURL).append(BASE_URL).append(entityName).append("/records").toString()))
                .header("Authorization", getToken())
                .POST(HttpRequest.BodyPublishers.ofString(JsonConverter.toJson(t)))
                .build();

        HttpResponse<String> response = getClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new ExportException(response.body());
        }

        return JsonConverter.fromJson(response.body(), classResponse);
    }

    public <T extends BaseEntidade> T update(String entityName, T t, Class<T> classResponse, String id) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .uri(URI.create(new StringBuilder(pocketbaseURL).append(BASE_URL).append(entityName).append("/records/").append(id).toString()))
                .header("Authorization", getToken())
                .method("PATCH", HttpRequest.BodyPublishers.ofString(JsonConverter.toJson(t)))
                .build();

        HttpResponse<String> response = getClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new ExportException(response.body());
        }
        return JsonConverter.fromJson(response.body(), classResponse);
    }

    public void delete(String entityName, String id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .uri(URI.create(new StringBuilder(pocketbaseURL).append(BASE_URL).append(entityName).append("/records/").append(id).toString()))
                .header("Authorization", getToken())
                .DELETE()
                .build();

        HttpResponse<String> response = getClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 204) {
            throw new ExportException(response.body());
        }
    }

    public <T extends BaseEntidade> T getOne(String entityName, Class<T> classResponse, String id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .uri(URI.create(new StringBuilder(pocketbaseURL).append(BASE_URL).append(entityName).append("/records/").append(id).toString()))
                .header("Authorization", getToken())
                .GET()
                .build();

        HttpResponse<String> response = getClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new ExportException(response.body());
        }
        return JsonConverter.fromJson(response.body(), classResponse);

    }

    private String getToken() throws IOException, InterruptedException {
        return "Bearer " + getLogin().getToken();
    }

    HttpClient getClient() {
        if (Objects.isNull(client)) {
            if (proxyHost.isPresent() && proxyPort.isPresent()) {
                this.client = HttpClient.newBuilder().proxy(ProxySelector.of(new InetSocketAddress(proxyHost.get(), proxyPort.get()))).build();
            } else {
                this.client = HttpClient.newHttpClient();
            }
        }
        return client;
    }
}
