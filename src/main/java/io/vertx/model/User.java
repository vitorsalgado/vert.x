package io.vertx.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {
    @JsonProperty("_id")
    private String id;
    private String name;
    private String email;

    public User(String name, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
    }

    User() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
