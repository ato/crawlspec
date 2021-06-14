package org.netpreserve.crawlspec.crawler;

import org.netpreserve.crawlspec.Crawler;
import org.netpreserve.crawlspec.job.Job;
import org.netpreserve.crawlspec.job.RobotsPolicy;
import org.netpreserve.crawlspec.job.Seed;
import org.netpreserve.crawlspec.util.Command;

public class Httrack implements Crawler {
    @Override
    public Command buildCommand(Job job) {
        Command command = new Command();
        command.add("httrack");
        command.addOption("--user-agent", job.getUserAgent());
        command.addOption("--max-size=", job.getSizeLimit());
        command.addOption("--max-time=", job.getTimeLimit());
        command.addOption("--sockets=", job.getWorkers());
        command.addOption("--robots=", mapRobotsPolicy(job.getRobotsPolicy()));
        for (Seed seed : job.getSeeds()) {
            command.add(seed.getUrl());
        }
        return command;
    }

    private Integer mapRobotsPolicy(RobotsPolicy robotsPolicy) {
        if (robotsPolicy == null) return null;
        switch (robotsPolicy) {
            case OBEY:
                return 2; // always
            case IGNORE:
                return 0; // never
            default:
                return null;
        }
    }
}
