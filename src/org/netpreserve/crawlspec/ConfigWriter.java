package org.netpreserve.crawlspec;

import org.netpreserve.crawlspec.job.Cookie;
import org.netpreserve.crawlspec.util.Command;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ConfigWriter {
    private final Path directory;

    public ConfigWriter(Path directory) {
        this.directory = directory;
    }

    public void writeFile(String filename, String contents) throws IOException {
        Files.write(directory.resolve(filename), contents.getBytes(UTF_8));
    }

    public void writeRunCommand(Command command) throws IOException {
        Files.write(directory.resolve("run.sh"), command.toString().getBytes(UTF_8));
    }

    public void writeCookiesTxt(List<Cookie> cookies) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(directory.resolve("cookies.txt"))) {
            for (Cookie cookie: cookies) {
                cookie.writeTo(writer);
            }
        }
    }
}
