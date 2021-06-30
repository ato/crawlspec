package org.netpreserve.crawlspec.job;

import java.util.ArrayList;
import java.util.List;

import static org.netpreserve.crawlspec.CrawlerId.*;

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

    /**
     * A list of cookies to pre-populate the crawler's cookie jar with (e.g. for authentication).
     */
    @SupportedBy({HERITRIX, HTTRACK, WGET})
    private List<Cookie> cookies;

    /**
     * An optional schedule for this crawl job as a five field cron expression.
     * <p>
     * Whitespace-separated fields: minute, hour, day of month, month of year, day of week (0=Sunday).<br>
     * Operators: inclusive range (2-4), list (2,3,6-8), every (*), interval (*&#47;5).
     * <p>
     * Examples:<br>
     * <code>0 *&#47;2 * * *</code> &mdash; run every two hours<br>
     * <code>0 9,18 * * 1-5</code> &mdash; run at 9 AM and 6 PM on weekdays
     */
    private String schedule;

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

    public List<Cookie> getCookies() {
        return cookies;
    }

    public boolean hasCookies() {
        return getCookies() != null && !getCookies().isEmpty();
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
