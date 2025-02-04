package br.gov.ce.sefaz;

import br.gov.ce.sefaz.chati.pocketbase.LoginRequest;
import br.gov.ce.sefaz.chati.pocketbase.LoginResponse;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import br.gov.ce.sefaz.chati.pocketbase.PocketBaseClientInterface;

/**
 *
 * @author gilmario
 */
public class PocketBaseTests {

    public static void main(String[] args) throws IOException {

        RestClientBuilder builder = RestClientBuilder.newBuilder();
//        if (proxyHost.isPresent() && proxyPort.isPresent()) {
//            builder = builder.proxyAddress(proxyHost.get(), proxyPort.get());
//        }

        LocalDateTime date = LocalDateTime.parse("2025-02-02 15:52:39.741Z", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS'Z'"));

        System.out.println(date);
        String url = "http://localhost:8080";

        PocketBaseClientInterface service = builder
                .baseUri(URI.create(url))
                //.register(GoogleChatServiceOption.class)
                .build(PocketBaseClientInterface.class);

        LoginResponse response = service.login(new LoginRequest("pocketbase@gilmariosoftware.com.br", "pocketbase"));
//        LoginResponse resp = JsonConverter.fromJson(response, LoginResponse.class);

        System.out.println(response);
    }
}
