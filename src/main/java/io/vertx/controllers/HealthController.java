package io.vertx.controllers;

import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;

public class HealthController {
    public void check(RoutingContext ctx) {
        ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/plain").end("ok");
    }
}
