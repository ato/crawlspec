package org.netpreserve.crawlspec.crawler;

import org.junit.jupiter.api.Test;
import org.netpreserve.crawlspec.job.JobTest;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HeritrixCrawlerTest {

    @Test
    void buildProperties() {
        Map<String,String> expected = new TreeMap<>();
        expected.put("crawlLimiter.maxBytesDownload", "100000000");
        expected.put("crawlLimiter.maxTimeSeconds", "3600");
        expected.put("metadata.jobName", "example-job");
        expected.put("metadata.userAgentTemplate", "crawlspec_bot/2.0");
        expected.put("cookieStore.cookiesLoadFile", "cookies.txt");
        assertEquals(expected, new Heritrix().buildProperties(JobTest.testJob).map());
    }
}