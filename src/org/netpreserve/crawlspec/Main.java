package org.netpreserve.crawlspec;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.netpreserve.crawlspec.job.Job;
import org.netpreserve.crawlspec.job.Seed;
import org.netpreserve.crawlspec.util.Command;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "crawlspec", description = "Runs a web crawl", mixinStandardHelpOptions = true)
public class Main implements Callable<Integer> {
    @Option(names = {"-c", "--crawler"}, description = "One of: ${COMPLETION-CANDIDATES}")
    private CrawlerId crawlerId = CrawlerId.WGET;

    @Option(names = {"-o", "--output-directory"}, description = "Directory to write output to")
    private Path outputDirectory = Paths.get(".");

    @CommandLine.Parameters(description = "URLs to visit initially")
    private List<String> seedUrls = new ArrayList<>();

    public static void main(String args[]) {
        System.exit(new CommandLine(new Main()).execute(args));
    }

    @Override
    public Integer call() throws Exception {
        Crawler crawler = crawlerId.newInstance();
        Job job = new Job();
        for (String seedUrl : seedUrls) {
            job.getSeeds().add(new Seed(seedUrl));
        }
        System.out.println("Config: " + new ObjectMapper().writeValueAsString(job));
        Command command = crawler.buildCommand(job);
        System.out.println("Command: " + command);

        if (!Files.exists(outputDirectory)) {
            Files.createDirectories(outputDirectory);
        }

        ProcessBuilder processBuilder = new ProcessBuilder(command.list());
        processBuilder.inheritIO();
        processBuilder.directory(outputDirectory.toFile());
        Process process = processBuilder.start();
        process.waitFor();
        return 0;
    }
}
