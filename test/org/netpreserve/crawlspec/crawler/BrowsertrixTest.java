package org.netpreserve.crawlspec.crawler;

import org.junit.jupiter.api.Test;
import org.netpreserve.crawlspec.job.JobTest;

import static org.junit.jupiter.api.Assertions.*;

class BrowsertrixTest {

    @Test
    void buildCommandLine() {
        assertEquals("docker run -it webrecorder/browsertrix-crawler crawl --userAgent crawlspec_bot/2.0 --url https://example.org/ --url https://www.example.com/foo/bar.html",
                new Browsertrix().buildCommand(JobTest.testJob).toString());
    }
}