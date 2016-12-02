package io.vertx.integration;

import io.vertx.Server;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;

import java.io.IOException;
import java.net.ServerSocket;

public class VertxHelper {
    private static Vertx vertx;
    private static boolean started;

    public static boolean isStarted() {
        return started;
    }

    public static Vertx init(TestContext context) throws IOException {
        if (isStarted()) {
            return vertx;
        }

        vertx = Vertx.vertx();

        ServerSocket socket = new ServerSocket(0);

        int port = socket.getLocalPort();

        socket.close();

        DeploymentOptions options = new DeploymentOptions()
                .setConfig(new JsonObject().put("http.port", port));

        vertx.deployVerticle(Server.class.getName(), options, context.asyncAssertSuccess());

        started = true;

        return vertx;
    }

    public static void finish(TestContext context) {
        if (isStarted()) {
            vertx.close(context.asyncAssertSuccess());
        }
    }
}
