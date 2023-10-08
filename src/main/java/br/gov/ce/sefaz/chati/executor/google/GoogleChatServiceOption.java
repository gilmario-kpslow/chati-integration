package br.gov.ce.sefaz.chati.executor.google;

import io.vertx.core.http.HttpClientOptions;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author gilmario
 */
@Provider
public class GoogleChatServiceOption implements ContextResolver<HttpClientOptions> {

    @Override
    public HttpClientOptions getContext(Class<?> type) {
        HttpClientOptions options = new HttpClientOptions();
        options.setSsl(false);

        return options;
    }

}
