package org.netpreserve.crawlspec.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JobTest {
    public static Job testJob;

    static {
        try {
            testJob = new ObjectMapper().readValue(JobTest.class.getResource("example-job.json"), Job.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test() throws IOException {
        assertEquals("example-job", testJob.getId());
    }
}