package io.vertx.configuration;

import io.vertx.config.Configuration;
import io.vertx.config.ConfigurationProvider;

public class TestConfigurationProvider implements ConfigurationProvider {
    @Override
    public Configuration getConfiguration() {
        return new Configuration(8080, "mongodb://localhost:27017", "user");
    }
}
