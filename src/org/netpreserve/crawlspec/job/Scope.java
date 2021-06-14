package org.netpreserve.crawlspec.job;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Scope {
    DOMAIN("domain"), HOST("host"), DIRECTORY("directory"), PAGE("page");

    private final String id;

    Scope(String id) {
        this.id = id;
    }

    @JsonValue
    public String getId() {
        return id;
    }
}
