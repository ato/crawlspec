package org.netpreserve.crawlspec.util;

import java.util.Map;
import java.util.TreeMap;

public class Properties {
    private final Map<String, String> map = new TreeMap<>();

    public void put(String name, String value) {
        if (value == null) return;
        map.put(name, value);
    }

    public void put(String name, Number value) {
        if (value == null) return;
        map.put(name, value.toString());
    }

    public Map<String,String> map() {
        return map;
    }

    public String serialize() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.append(entry.getKey());
            builder.append('=');
            builder.append(entry.getValue());
            builder.append('\n');
        }
        return builder.toString();
    }

    public String toString() {
        return map.toString();
    }
}
