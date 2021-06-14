package org.netpreserve.crawlspec.crawler;

import org.junit.jupiter.api.Test;
import org.netpreserve.crawlspec.job.JobTest;

import static org.junit.jupiter.api.Assertions.*;

class HttrackTest {
    @Test
    void buildCommandLine() {
        assertEquals("httrack --user-agent crawlspec_bot/2.0 --max-size==100000000 --max-time==3600 --robots==2 https://example.org/ https://www.example.com/foo/bar.html",
                new Httrack().buildCommand(JobTest.testJob).toString());
    }
}