package org.samiha.playground;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.samiha.verticles.ListenerVerticle;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ListenerVerticle("First"), stringAsyncResult -> {
            System.out.println("First Verticle deployment complete");
        });

        vertx.deployVerticle(new ListenerVerticle("Second"), stringAsyncResult -> {
            System.out.println("Second Verticle deployment complete");
        });

        HttpServer httpServer = vertx.createHttpServer();

        Router router = Router.router(vertx);
        Route handler1 = router.route("/hello/:name").handler(routingContext -> {

            String name = routingContext.pathParam("name");
            vertx.eventBus().send("test","message 1");
            vertx.eventBus().publish("test1","message 2");

            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type","text/plain");
            response.end("Hello " + name);
        });

        httpServer.requestHandler(router).listen(8090);
    }
}
