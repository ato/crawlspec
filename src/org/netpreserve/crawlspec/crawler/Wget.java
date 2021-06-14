package org.netpreserve.crawlspec.crawler;

import org.netpreserve.crawlspec.Crawler;
import org.netpreserve.crawlspec.job.Job;
import org.netpreserve.crawlspec.job.RobotsPolicy;
import org.netpreserve.crawlspec.job.Seed;
import org.netpreserve.crawlspec.util.Command;

public class Wget implements Crawler {
    @Override
    public Command buildCommand(Job job) {
        Command command = new Command();
        command.add("wget");
        command.add("--recursive");
        command.add("--page-requisites");
        command.addOption("--user-agent=", job.getUserAgent());
        command.addOption("--quota=", job.getSizeLimit());
        if (job.getRobotsPolicy() == RobotsPolicy.IGNORE) {
            command.addOption("--execute", "robots=off");
        }
        for (Seed seed : job.getSeeds()) {
            command.add(seed.getUrl());
        }
        return command;
    }
}
