package org.netpreserve.crawlspec.crawler;

import org.junit.jupiter.api.Test;
import org.netpreserve.crawlspec.job.JobTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WgetTest {
    @Test
    void buildCommandLine() {
        assertEquals("wget --recursive --page-requisites --user-agent=crawlspec_bot/2.0 --quota==100000000 https://example.org/ https://www.example.com/foo/bar.html",
                new Wget().buildCommand(JobTest.testJob).toString());
    }
}