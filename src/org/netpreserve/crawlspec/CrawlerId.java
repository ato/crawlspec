package org.netpreserve.crawlspec;

import org.netpreserve.crawlspec.crawler.Browsertrix;
import org.netpreserve.crawlspec.crawler.Heritrix;
import org.netpreserve.crawlspec.crawler.Httrack;
import org.netpreserve.crawlspec.crawler.Wget;

import java.util.Locale;
import java.util.function.Supplier;

public enum CrawlerId {
    BROWSERTRIX(Browsertrix::new),
    HERITRIX(Heritrix::new),
    HTTRACK(Httrack::new),
    WGET(Wget::new);

    private final Supplier<Crawler> constructor;

    CrawlerId(Supplier<Crawler> constructor) {
        this.constructor = constructor;
    }

    public Crawler newInstance() {
        return constructor.get();
    }

    public String toString() {
        return name().toLowerCase(Locale.ROOT);
    }
}
