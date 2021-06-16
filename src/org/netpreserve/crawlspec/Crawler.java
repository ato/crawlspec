package org.netpreserve.crawlspec;

import org.netpreserve.crawlspec.job.Job;
import org.netpreserve.crawlspec.util.Command;

import java.io.IOException;

public interface Crawler {
    void writeConfig(Job job, ConfigWriter configWriter) throws IOException;

    Command buildCommand(Job job);
}
