package io.vertx;

import io.vertx.config.ConfigurationProvider;
import io.vertx.config.EnvironmentConfigurationProvider;
import io.vertx.configuration.TestConfigurationProvider;
import io.vertx.repositories.MongodbUserRepository;
import io.vertx.repositories.UserRepository;

public final class Injection {
    public static ConfigurationProvider provideConfiguration() {
        return new TestConfigurationProvider();
    }

    public static UserRepository provideUserRepository() {
        return MongodbUserRepository.getInstance(
                provideConfiguration());
    }
}
