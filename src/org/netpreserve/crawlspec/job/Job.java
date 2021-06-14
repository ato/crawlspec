package org.netpreserve.crawlspec.job;

import java.util.ArrayList;
import java.util.List;

import static org.netpreserve.crawlspec.CrawlerId.*;
import static org.netpreserve.crawlspec.CrawlerId.HTTRACK;

public class Job {
    private List<Seed> seeds = new ArrayList<>();

    /**
     * Identifier or name of the job.
     */
    @SupportedBy({HERITRIX})
    private String id;

    /**
     * Stop the crawl job after this many bytes have elapsed.
     */
    @SupportedBy({HERITRIX, HTTRACK, WGET})
    private Long sizeLimit;

    /**
     * Stop the crawl job after this many seconds have elapsed.
     */
    @SupportedBy({HERITRIX, HTTRACK})
    private Integer timeLimit;

    /**
     * User-Agent header the crawler identifies itself as.
     */
    @SupportedBy({BROWSERTRIX, HERITRIX, HTTRACK, WGET})
    private String userAgent;

    /**
     * Maximum number of worker threads or concurrent connections.
     */
    @SupportedBy({BROWSERTRIX, HERITRIX, HTTRACK})
    private Integer workers;

    /**
     * Whether to obey or ignore robots.txt (Robots exclusion standard).
     */
    @SupportedBy({HTTRACK, HERITRIX, WGET})
    private RobotsPolicy robotsPolicy;

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Long getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(Long sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getWorkers() {
        return workers;
    }

    public void setWorkers(Integer workers) {
        this.workers = workers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Seed> getSeeds() {
        return seeds;
    }

    public void setSeeds(List<Seed> seeds) {
        this.seeds = seeds;
    }

    public RobotsPolicy getRobotsPolicy() {
        return robotsPolicy;
    }

    public void setRobotsPolicy(RobotsPolicy robotsPolicy) {
        this.robotsPolicy = robotsPolicy;
    }
}
