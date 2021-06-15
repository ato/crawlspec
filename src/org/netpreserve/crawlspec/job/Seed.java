package org.netpreserve.crawlspec.job;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Seed {
    @JsonProperty(required = true)
    private String url;
    private Scope scope;

    public Seed() {
    }

    public Seed(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
