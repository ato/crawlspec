package org.netpreserve.crawlspec;

import org.netpreserve.crawlspec.job.Job;
import org.netpreserve.crawlspec.util.Command;

public interface Crawler {
    Command buildCommand(Job job);
}
