package org.netpreserve.crawlspec.crawler;

import org.netpreserve.crawlspec.ConfigWriter;
import org.netpreserve.crawlspec.Crawler;
import org.netpreserve.crawlspec.job.Job;
import org.netpreserve.crawlspec.job.RobotsPolicy;
import org.netpreserve.crawlspec.job.Scope;
import org.netpreserve.crawlspec.job.Seed;
import org.netpreserve.crawlspec.util.Command;
import org.netpreserve.crawlspec.util.Properties;
import org.netpreserve.urlcanon.Canonicalizer;
import org.netpreserve.urlcanon.ParsedUrl;

import java.io.IOException;
import java.util.stream.Stream;

public class Heritrix implements Crawler {
    @Override
    public void writeConfig(Job job, ConfigWriter configWriter) throws IOException {
        configWriter.writeRunCommand(buildCommand(job));
        configWriter.writeFile("overrides.properties", buildOverrides(job).serialize());
        configWriter.writeFile("surts.txt", buildSurtsTxt(job));
        configWriter.writeFile("seeds.txt", buildSeedsTxt(job));
        if (job.hasCookies()) {
            configWriter.writeCookiesTxt(job.getCookies());
        }
    }

    @Override
    public Command buildCommand(Job job) {
        Command command = new Command();
        command.add("heritrix");
        command.addOption("-r", job.getId());
        return command;
    }

    public Properties buildOverrides(Job job) {
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

    public ParsedUrl parseAndCanonicalize(String url) {
        ParsedUrl parsedUrl = ParsedUrl.parseUrl(url);

        // default to http:// if there's no scheme
        if (parsedUrl.getScheme().isEmpty()) {
            parsedUrl = ParsedUrl.parseUrl("http://" + url);
        }

        Canonicalizer.WHATWG.canonicalize(parsedUrl);
        return parsedUrl;
    }

    private Stream<String> buildSeedsTxt(Job job) {
        return job.getSeeds().stream().map(Seed::getUrl);
    }

    private Stream<String> buildSurtsTxt(Job job) {
        return job.getSeeds().stream().map(this::buildSurt);
    }

    public String buildSurt(Seed seed) {
        return buildSurt(seed.getUrl(), seed.getScope());
    }

    public String buildSurt(String url, Scope scope) {
        ParsedUrl parsedUrl = parseAndCanonicalize(url);
        if (scope == null) {
            scope = parsedUrl.getPath().equals("/") ? Scope.DOMAIN : Scope.DIRECTORY;
        }
        switch (scope) {
            case PAGE:
                return parsedUrl.surt();
            case DIRECTORY:
                parsedUrl.setFragment("");
                parsedUrl.setHashSign("");
                parsedUrl.setQuery("");
                parsedUrl.setQuestionMark("");
                parsedUrl.setPath(parsedUrl.getPath().replaceFirst("[^/]+$", ""));
                return parsedUrl.surt();
            case HOST:
                parsedUrl.setFragment("");
                parsedUrl.setHashSign("");
                parsedUrl.setQuery("");
                parsedUrl.setQuestionMark("");
                parsedUrl.setPath("/");
                return parsedUrl.surt();
            case DOMAIN:
                parsedUrl.setFragment("");
                parsedUrl.setHashSign("");
                parsedUrl.setQuery("");
                parsedUrl.setQuestionMark("");
                parsedUrl.setPath("");
                parsedUrl.setPort("");
                parsedUrl.setColonBeforePort("");
                if (parsedUrl.getHost().startsWith("www.")) {
                    parsedUrl.setHost(parsedUrl.getHost().substring("www.".length()));
                }
                return parsedUrl.surt().replaceFirst("\\)$", "");
            default:
                throw new IllegalArgumentException("unimplemented scope: " + scope);
        }
    }
}
