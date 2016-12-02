package io.vertx.repositories;

import io.vertx.model.User;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface UserRepository {
    CompletableFuture<Optional<User>> getById(String id);

    CompletableFuture<Void> add(User user);
}
