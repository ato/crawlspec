package org.netpreserve.crawlspec.job;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CookieTest {
    @Test
    public void cookiesTxt() {
        String line = "example.com\tFALSE\t/foo\tTRUE\t123456789\tname\tvalue\n";
        Cookie cookie = Cookie.fromTxt(line);
        assertEquals("example.com", cookie.getDomain());
        assertEquals("/foo", cookie.getPath());
        assertEquals("name", cookie.getName());
        assertEquals("value", cookie.getValue());
        assertTrue(cookie.isSecure());
        assertEquals(line, cookie.toTxt());
    }
}