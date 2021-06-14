package org.netpreserve.crawlspec.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command {
    private final List<String> list = new ArrayList<>();

    public void add(String... values) {
        list.addAll(Arrays.asList(values));
    }

    public void addOption(String name, String value) {
        if (value == null) return;
        if (name.endsWith("=")) {
            list.add(name + value);
        } else {
            list.add(name);
            list.add(value);
        }
    }

    public void addOption(String name, Number value) {
        if (value == null) return;
        if (name.endsWith("=")) {
            list.add(name + "=" + value);
        } else {
            list.add(name);
            list.add(value.toString());
        }
    }

    @Override
    public String toString() {
        return String.join(" ", list);
    }

    public List<String> list() {
        return list;
    }
}
