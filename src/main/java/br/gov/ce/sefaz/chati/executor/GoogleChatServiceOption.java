/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gov.ce.sefaz.chati.executor;

import io.vertx.core.http.HttpClientOptions;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

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
