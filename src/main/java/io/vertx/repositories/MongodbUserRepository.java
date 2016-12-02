package io.vertx.repositories;

import io.vertx.config.Configuration;
import io.vertx.config.ConfigurationProvider;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.model.User;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class MongodbUserRepository implements UserRepository {
    private static class Holder {
        private static MongodbUserRepository instance(ConfigurationProvider configurationProvider) {
            return new MongodbUserRepository(configurationProvider);
        }
    }

    public static MongodbUserRepository getInstance(ConfigurationProvider configurationProvider) {
        return MongodbUserRepository.Holder.instance(configurationProvider);
    }

    private static final String CONFIG_MONGO_URI = "mongo_uri";
    private static final String CONFIG_MONGO_COLLECTION = "db_name";

    private final MongoClient client;
    private final String collection;

    public MongodbUserRepository(ConfigurationProvider configurationProvider) {
        if (configurationProvider == null) {
            throw new IllegalArgumentException("configurationProvider cannot be null.");
        }

        Configuration configuration = configurationProvider.getConfiguration();
        String uri = configuration.getMongoUri();
        String db = configuration.getDb();

        JsonObject config = new JsonObject()
                .put(CONFIG_MONGO_URI, uri)
                .put(CONFIG_MONGO_COLLECTION, db);

        collection = db;
        client = MongoClient.createShared(Vertx.vertx(), config);
    }

    @Override
    public CompletableFuture<Optional<User>> getById(String id) {
        CompletableFuture<Optional<User>> promise = new CompletableFuture<>();

        client.findOne(collection, new JsonObject(), new JsonObject(), event -> {
            if (event.succeeded()) {
                promise.complete(Optional.ofNullable(
                        Json.decodeValue(event.result().toString(), User.class)));
                return;
            }

            promise.complete(Optional.empty());
        });

        return promise;
    }

    @Override
    public CompletableFuture<Void> add(User user) {
        CompletableFuture<Void> promise = new CompletableFuture<>();

        client.insert(collection, new JsonObject(Json.encode(user)), event -> {
            if (event.succeeded()) {
                promise.complete(null);
                return;
            }

            promise.completeExceptionally(event.cause());
        });

        return promise;
    }
}
