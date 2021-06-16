package org.netpreserve.crawlspec.crawler;

import org.netpreserve.crawlspec.ConfigWriter;
import org.netpreserve.crawlspec.Crawler;
import org.netpreserve.crawlspec.job.Job;
import org.netpreserve.crawlspec.job.RobotsPolicy;
import org.netpreserve.crawlspec.util.Command;
import org.netpreserve.crawlspec.util.Properties;

import java.io.IOException;

public class Heritrix implements Crawler {
    @Override
    public void writeConfig(Job job, ConfigWriter configWriter) throws IOException {
        configWriter.writeRunCommand(buildCommand(job));
        configWriter.writeFile("override.properties", buildProperties(job).serialize());
        if (job.hasCookies()) {
            configWriter.writeCookiesTxt(job.getCookies());
        }
    }

    @Override
    public Command buildCommand(Job job) {
        Command command = new Command();
        command.add("heritrix");
        return command;
    }

    public Properties buildProperties(Job job) {
        Properties properties = new Properties();
        properties.put("metadata.jobName", job.getId());
        properties.put("metadata.userAgentTemplate", job.getUserAgent());
        properties.put("crawlLimiter.maxBytesDownload", job.getSizeLimit());
        properties.put("crawlLimiter.maxTimeSeconds", job.getTimeLimit());
        properties.put("crawlController.maxToeThreads", job.getWorkers());
        if (job.getRobotsPolicy() == RobotsPolicy.IGNORE) {
            properties.put("metadata.robotsPolicyName", "ignore");
        }
        if (job.hasCookies()) {
            properties.put("cookieStore.cookiesLoadFile", "cookies.txt");
        }
        return properties;
    }
}
