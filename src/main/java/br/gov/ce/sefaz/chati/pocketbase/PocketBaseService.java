package br.gov.ce.sefaz.chati.pocketbase;

import br.gov.ce.sefaz.chati.core.BaseEntidade;
import br.gov.ce.sefaz.chati.utils.JsonConverter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author gilmario
 */
@ApplicationScoped
public class PocketBaseService {

    private static final String BASE_URL = "/api/collections/";
    @ConfigProperty(name = "app.pocketbase.url")
    String pocketbaseURL;
    @ConfigProperty(name = "app.pocketbase.username")
    private String pocketbaseUsuario;
    @ConfigProperty(name = "app.pocketbase.password")
    private String pocketbaseSenha;
    private LoginResponse loginResponse;
    private final HttpClient client;

    public PocketBaseService() {
        this.client = HttpClient.newHttpClient();
    }

    public LoginResponse getLogin() throws IOException, InterruptedException {
        if (Objects.nonNull(loginResponse)) {
            return loginResponse;
        }
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .uri(URI.create(new StringBuilder(pocketbaseURL).append(BASE_URL).append("_superusers/auth-with-password").toString()))
                .POST(HttpRequest.BodyPublishers.ofString(JsonConverter.toJson(new LoginRequest(pocketbaseUsuario, pocketbaseSenha))))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        loginResponse = JsonConverter.fromJson(response.body(), LoginResponse.class);

        return loginResponse;
    }

    public <T extends BaseEntidade> PageResponse<T> listar(String entityName, Class<T> classResponse) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .uri(URI.create(new StringBuilder(pocketbaseURL).append(BASE_URL).append(entityName).append("/records").toString()))
                .header("Authorization", getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

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

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

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

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

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

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    }

    public T getOne(String entityName, String id) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .uri(URI.create(new StringBuilder(pocketbaseURL).append(BASE_URL).append(entityName).append("/records/").append(id).toString()))
                .header("Authorization", getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return JsonConverter.fromJson(response.body(), classResponse);

    }

    private String getToken() throws IOException, InterruptedException {
        return "Bearer " + getLogin().getToken();
    }
}
