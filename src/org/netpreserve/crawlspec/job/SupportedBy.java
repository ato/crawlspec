package org.netpreserve.crawlspec.job;

import org.netpreserve.crawlspec.CrawlerId;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SupportedBy {
    CrawlerId[] value();
}
