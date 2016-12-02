package io.vertx.controllers;

import io.vertx.Injection;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import io.vertx.model.User;
import io.vertx.repositories.UserRepository;

import static io.netty.handler.codec.http.HttpResponseStatus.*;

public class UserController {
    public void get(RoutingContext ctx) {
        String id = ctx.request().getParam("id");

        if (id == null || id.isEmpty()) {
            ctx.response().setStatusCode(BAD_REQUEST.code()).end("user id is required");
            return;
        }

        UserRepository userRepository = Injection.provideUserRepository();

        userRepository.getById(id)
                .thenAcceptAsync(optional -> {
                    ctx.response()
                            .putHeader(HttpHeaders.CONTENT_TYPE, "application/json");

                    if (optional.isPresent()) {
                        ctx.response()
                                .setChunked(true)
                                .write(Json.encode(optional.get()))
                                .end();
                        return;
                    }

                    ctx.response().setStatusCode(NOT_FOUND.code()).end();
                });
    }

    public void add(RoutingContext ctx) {
        User incomingUser = Json.decodeValue(ctx.getBodyAsString(), User.class);

        if (incomingUser == null) {
            ctx.response().setStatusCode(BAD_REQUEST.code()).end("invalid user data");
            return;
        }

        UserRepository userRepository = Injection.provideUserRepository();

        userRepository.add(incomingUser)
                .thenComposeAsync(x -> userRepository.getById(incomingUser.getId()))
                .thenAcceptAsync(optional ->
                        ctx.response()
                                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                                .setChunked(true)
                                .setStatusCode(CREATED.code())
                                .write(Json.encode(optional.get()))
                                .end())
                .exceptionally(error -> {
                    ctx.fail(error);
                    return null;
                });
    }

}
