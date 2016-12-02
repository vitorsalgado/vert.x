package io.vertx.config;

public class EnvironmentConfigurationProvider implements ConfigurationProvider {
    private static class Holder {
        private static final EnvironmentConfigurationProvider instance = new EnvironmentConfigurationProvider();
    }

    public static EnvironmentConfigurationProvider getInstance() {
        return Holder.instance;
    }

    @Override
    public Configuration getConfiguration() {
        int port = Integer.parseInt(System.getenv("PORT"));
        String mongoUri = System.getenv("MONGO_URI");
        String db = System.getenv("DB");

        return new Configuration(port, mongoUri, db);
    }
}
