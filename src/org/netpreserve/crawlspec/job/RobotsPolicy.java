package org.netpreserve.crawlspec.job;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RobotsPolicy {
    /**
     * Ignore robots.txt entirely.
     */
    IGNORE("ignore"),

    /**
     * Ovey at least to the page-level. The crawler not follow robots.txt for subrequests to transcluded content.
     */
    OBEY("obey"),

    /**
     * Obey strictly. For example following robots.txt even for subrequests for transcluded content.
     */
    STRICT("strict");

    private final String id;

    RobotsPolicy(String id) {
        this.id = id;
    }

    @JsonValue
    public String getId() {
        return id;
    }
}
