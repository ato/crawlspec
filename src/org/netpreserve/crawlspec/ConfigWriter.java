package org.netpreserve.crawlspec;

import org.netpreserve.crawlspec.job.Cookie;
import org.netpreserve.crawlspec.util.Command;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ConfigWriter {
    private final Path directory;

    public ConfigWriter(Path directory) {
        this.directory = directory;
    }

    public void writeFile(String filename, Stream<String> lines) throws IOException {
        Files.write(directory.resolve(filename), (Iterable<String>)lines::iterator);
    }

    public void writeFile(String filename, String contents) throws IOException {
        Files.write(directory.resolve(filename), contents.getBytes(UTF_8));
    }

    public void writeRunCommand(Command command) throws IOException {
        Path runScript = directory.resolve("run.sh");
        Files.write(runScript, ("#!/bin/sh\nexec " + command + "\n").getBytes(UTF_8));
        try {
            Files.setPosixFilePermissions(runScript, PosixFilePermissions.fromString("rwxr-xr-x"));
        } catch (UnsupportedOperationException e) {
            // oh well
        }
    }

    public void writeCrontab(String schedule) throws IOException {
        writeFile("crontab", schedule + " cd " +
                Command.shellEscape(directory.toAbsolutePath().toString()) + " && ./run.sh\n");
    }

    public void writeCookiesTxt(List<Cookie> cookies) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(directory.resolve("cookies.txt"))) {
            for (Cookie cookie: cookies) {
                cookie.writeTo(writer);
            }
        }
    }
}
