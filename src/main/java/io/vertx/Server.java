package io.vertx;

import io.vertx.config.Configuration;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class Server extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(Server.class.getName());
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Configuration configuration = Injection.provideConfiguration().getConfiguration();
        Vertx vertx = Vertx.vertx();

        Router router = Routes.buildRoutes(vertx);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(configuration.getPort(), event -> {
                    if (event.succeeded()) {
                        startFuture.complete();
                    } else {
                        startFuture.fail(event.cause());
                    }
                });
    }
}
