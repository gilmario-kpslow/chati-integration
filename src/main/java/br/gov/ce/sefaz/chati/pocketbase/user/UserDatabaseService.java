package br.gov.ce.sefaz.chati.pocketbase.user;

import br.gov.ce.sefaz.chati.core.DatabaseService;
import br.gov.ce.sefaz.chati.pocketbase.LoginRequest;
import br.gov.ce.sefaz.chati.utils.JsonConverter;
import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author gilmario
 */
@Dependent
public class UserDatabaseService extends DatabaseService<User> {

    private final HttpClient client;

    public UserDatabaseService() {
        this.client = HttpClient.newHttpClient();
    }

    @Override
    protected Class<User> getClassEntity() {
        return User.class;
    }

    public void login() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Accept", MediaType.APPLICATION_JSON)
                .uri(URI.create(new StringBuilder()
                        .append(getPocketService().getPocketbaseURL())
                        .append("/api/collections/")
                        .append(getEntityName()).append("/auth-with-password").toString()))
                .POST(HttpRequest.BodyPublishers.ofString(JsonConverter.toJson(new LoginRequest("gilmario@gmail.com", "kpslow@0909"))))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    @Override
    protected String getEntityName() {
        return "_pb_users_auth_";
    }

}
