package org.netpreserve.crawlspec.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
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

    private static final BitSet shellEscapeChars = new BitSet();
    static {
        "|&;<>()$`\\\"' *?[#~%".chars().forEach(shellEscapeChars::set);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String word: list) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            shellEscape(word, builder);
        }
        return builder.toString();
    }

    private static void shellEscape(String string, StringBuilder builder) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c == '\r') {
                builder.append("$'\\r'");
            } else if (c == '\n') {
                builder.append("$'\\n'");
            } else if (c == '\t') {
                builder.append("$'\\t'");
            } else {
                if (shellEscapeChars.get(c)) {
                    builder.append('\\');
                }
                builder.append(c);
            }
        }
    }

   public static String shellEscape(String string) {
        StringBuilder builder = new StringBuilder();
        shellEscape(string, builder);
        return builder.toString();
    }

    public List<String> list() {
        return list;
    }
}
