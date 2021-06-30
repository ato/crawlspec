package org.netpreserve.crawlspec;

import org.netpreserve.crawlspec.job.Job;
import org.netpreserve.crawlspec.util.Command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CrawlSpec {
    public void build(Crawler crawler, Job job, Path outputDirectory) throws IOException {
        if (!Files.exists(outputDirectory)) {
            Files.createDirectories(outputDirectory);
        }
        ConfigWriter configWriter = new ConfigWriter(outputDirectory);
        crawler.writeConfig(job, configWriter);
        if (job.getSchedule() != null) {
            configWriter.writeCrontab(job.getSchedule());
        }
    }

    public void run(Crawler crawler, Job job, Path outputDirectory) throws IOException, InterruptedException {
        build(crawler, job, outputDirectory);

        Command command = crawler.buildCommand(job);
        System.out.println("Command: " + command);

        ProcessBuilder processBuilder = new ProcessBuilder(command.list());
        processBuilder.inheritIO();
        processBuilder.directory(outputDirectory.toFile());
        Process process = processBuilder.start();
        process.waitFor();
    }
}
