package org.netpreserve.crawlspec.crawler;

import org.netpreserve.crawlspec.Crawler;
import org.netpreserve.crawlspec.job.Job;
import org.netpreserve.crawlspec.job.Seed;
import org.netpreserve.crawlspec.util.Command;

public class Browsertrix implements Crawler {
    @Override
    public Command buildCommand(Job job) {
        Command command = new Command();
        command.add("docker", "run", "-it", "webrecorder/browsertrix-crawler", "crawl");
        command.addOption("--userAgent", job.getUserAgent());
        command.addOption("--workers", job.getWorkers());
        for (Seed seed : job.getSeeds()) {
            command.addOption("--url", seed.getUrl());
        }
        return command;
    }
}
