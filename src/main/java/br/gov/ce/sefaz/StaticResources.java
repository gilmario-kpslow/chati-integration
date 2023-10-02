/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.gov.ce.sefaz;

import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import jakarta.enterprise.event.Observes;

/**
 *
 * @author gilmario
 */
public class StaticResources {

    public void installRoute(@Observes StartupEvent startupEvent, Router router) {
        router.route()
                .path("/assets/**")
                .handler(StaticHandler.create("assets/"));
    }
}
