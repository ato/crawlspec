package org.netpreserve.crawlspec.job;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Field naming follows https://developer.mozilla.org/en-US/docs/Mozilla/Add-ons/WebExtensions/API/cookies/Cookie
 */
public class Cookie {
    @JsonProperty(required = true)
    private String name;
    @JsonProperty(required = true)
    private String value;
    @JsonProperty(required = true)
    private String domain;
    private boolean hostOnly;
    @JsonProperty(required = true)
    private String path;
    private boolean secure;
    private boolean httpOnly;
    private Long expirationDate;

    /**
     * Parses a Netscape-style cookies.txt line.
     */
    public static Cookie fromTxt(String line) {
        if (line.endsWith("\n")) {
            line = line.substring(0, line.length() - 1);
        }
        String[] fields = line.split("\t");
        Cookie cookie = new Cookie();
        cookie.setDomain(fields[0]);
        boolean includeSubdomains = parseBoolean(fields[1]);
        cookie.setHostOnly(!includeSubdomains);
        cookie.setPath(fields[2]);
        cookie.setSecure(parseBoolean(fields[3]));
        long expirationDate = Long.parseLong(fields[4]);
        cookie.setExpirationDate(expirationDate == 0 ? null : expirationDate);
        cookie.setName(fields[5]);
        cookie.setValue(fields[6]);
        return cookie;
    }

    public String toTxt() {
        StringBuilder builder = new StringBuilder();
        try {
            toTxt(builder);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return builder.toString();
    }

    public void toTxt(Appendable writer) throws IOException {
        boolean includeSubdomains = !isHostOnly();
        writer.append(getDomain());
        writer.append('\t');
        writer.append(formatBoolean(includeSubdomains));
        writer.append('\t');
        writer.append(getPath());
        writer.append('\t');
        writer.append(formatBoolean(isSecure()));
        writer.append('\t');
        writer.append(Long.toString(getExpirationDate() == null ? 0 : getExpirationDate()));
        writer.append('\t');
        writer.append(getName());
        writer.append('\t');
        writer.append(getValue());
        writer.append('\n');
    }

    private static String formatBoolean(boolean value) {
        return value ? "TRUE" : "FALSE";
    }

    private static boolean parseBoolean(String string) {
        if (string.equalsIgnoreCase("TRUE")) return true;
        if (string.equalsIgnoreCase("FALSE")) return false;
        throw new IllegalArgumentException(string);
    }

    public boolean isHostOnly() {
        return hostOnly;
    }

    public void setHostOnly(boolean hostOnly) {
        this.hostOnly = hostOnly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public Long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Long expirationDate) {
        this.expirationDate = expirationDate;
    }
}
