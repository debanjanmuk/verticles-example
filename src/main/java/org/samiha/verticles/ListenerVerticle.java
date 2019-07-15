package org.samiha.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

public class ListenerVerticle extends AbstractVerticle {

    private String name = null;

    public ListenerVerticle(String name) {
        this.name = name;
    }

    @Override
    public void start() throws Exception {
        vertx.eventBus().consumer("test",message -> {
            System.out.println("Received Message : " + message.body() + " : " + name);
        });
        System.out.println("Verticle Started : " + this.name);
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Verticle Stopped");
    }
}
