package org.netpreserve.crawlspec.job;

public class Seed {
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
