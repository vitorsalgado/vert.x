package io.vertx;

import io.vertx.config.ConfigurationProvider;
import io.vertx.config.EnvironmentConfigurationProvider;
import io.vertx.repositories.MongodbUserRepository;
import io.vertx.repositories.UserRepository;

public final class Injection {
    public static ConfigurationProvider provideConfiguration() {
        return EnvironmentConfigurationProvider.getInstance();
    }

    public static UserRepository provideUserRepository() {
        return MongodbUserRepository.getInstance(
                provideConfiguration());
    }
}
