package org.netpreserve.crawlspec.crawler;

import org.netpreserve.crawlspec.ConfigWriter;
import org.netpreserve.crawlspec.Crawler;
import org.netpreserve.crawlspec.job.Job;
import org.netpreserve.crawlspec.job.RobotsPolicy;
import org.netpreserve.crawlspec.job.Seed;
import org.netpreserve.crawlspec.util.Command;

import java.io.IOException;

public class Wget implements Crawler {
    @Override
    public void writeConfig(Job job, ConfigWriter configWriter) throws IOException {
        configWriter.writeRunCommand(buildCommand(job));
        if (job.hasCookies()) {
            configWriter.writeCookiesTxt(job.getCookies());
        }
    }

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
        if (job.hasCookies()) {
            command.addOption("--load-cookies", "cookies.txt");
        }
        for (Seed seed : job.getSeeds()) {
            command.add(seed.getUrl());
        }
        return command;
    }
}
