package org.netpreserve.crawlspec.crawler;

import org.junit.jupiter.api.Test;
import org.netpreserve.crawlspec.job.JobTest;
import org.netpreserve.crawlspec.job.Scope;

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
        assertEquals(expected, new Heritrix().buildOverrides(JobTest.testJob).map());
    }

    @Test
    public void buildSurt() {
        Heritrix heritrix = new Heritrix();
        assertEquals("http://(com,example,www,)/dir/index.html", heritrix.buildSurt("http://www.example.com/dir/index.html", Scope.PAGE));
        assertEquals("http://(com,example,www,)/dir/", heritrix.buildSurt("http://www.example.com/dir/index.html?q#f", Scope.DIRECTORY));
        assertEquals("http://(com,example,www,:81)/", heritrix.buildSurt("http://www.example.com:81/dir/index.html?q#f", Scope.HOST));
        assertEquals("http://(com,example,", heritrix.buildSurt("http://www.example.com:81/dir/index.html?q#f", Scope.DOMAIN));
        assertEquals("http://(com,example,www,)/", heritrix.buildSurt("http://www.example.com", Scope.PAGE));
        assertEquals("http://(com,example,", heritrix.buildSurt("http://www.example.com", null));
        assertEquals("http://(com,example,www,)/", heritrix.buildSurt("http://www.example.com/foo.html", null));
    }
}