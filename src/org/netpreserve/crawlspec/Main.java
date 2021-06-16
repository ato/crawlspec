package org.netpreserve.crawlspec;

import org.netpreserve.crawlspec.job.Job;
import org.netpreserve.crawlspec.job.Seed;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void usage() {
        System.out.println("Usage: crawlspec subcommand [options]\n" +
                "\n" +
                "Subcommands:\n" +
                "  build     Constructs a crawl job from a spec file\n" +
                "  run       Run a crawl job\n");
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            usage();
            return;
        }

        switch (args[0]) {
            case "-h":
            case "--help":
            case "help":
                usage();
                break;
            case "run":
                run(args);
                break;
            default:
                System.err.println("crawlspec: unknown subcommand: " + args[0]);
                System.exit(1);
                break;
        }

    }

    public static void run(String[] args) throws Exception {
        Path outputDirectory = Paths.get(".");
        CrawlerId crawlerId = null;
        List<String> seedUrls = new ArrayList<>();

        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "-h":
                case "--help":
                    runUsage();
                    return;
                case "-o":
                case "--output-directory":
                    outputDirectory = Paths.get(args[++i]);
                    break;
                default:
                    if (crawlerId == null) {
                        crawlerId = CrawlerId.valueOf(args[i].toUpperCase(Locale.ROOT));
                    } else {
                        seedUrls.add(args[i]);
                    }
            }
        }
        if (crawlerId == null) {
            System.err.println("A crawler must be specified");
            System.err.println();
            runUsage();
            System.exit(1);
        }

        Job job = new Job();
        for (String seedUrl : seedUrls) {
            job.getSeeds().add(new Seed(seedUrl));
        }
        new CrawlSpec().run(crawlerId.newInstance(), job, outputDirectory);
    }

    private static void runUsage() {
        System.out.println("Usage: crawlspec run <crawler> [seed-urls...]\n" +
                "\n" +
                "Options:" +
                "  -o --output-directory DIR    Directory to write the crawl job to");
    }
}
