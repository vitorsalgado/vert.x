package io.vertx.config;

public class Configuration {
    private int port;
    private String mongoUri;
    private String db;

    public Configuration(int port, String mongoUri, String db) {
        this.port = port;
        this.mongoUri = mongoUri;
        this.db = db;
    }

    public int getPort() {
        return port;
    }

    public String getMongoUri() {
        return mongoUri;
    }

    public String getDb() {
        return db;
    }
}
