package io.ryecrow.joe.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

class FiletimeUtilsTest {

    @Test
    void testLongFiletimeToInstant() {
        // FILETIME is defined as "100 nanoseconds since the midnight of 1st January 1601"
        Instant epoch = LocalDateTime.of(1601, Month.JANUARY, 1, 0, 0, 0, 0)
                .atZone(ZoneOffset.UTC).toInstant();
        Instant now = Instant.now();

        long seconds = epoch.until(now, ChronoUnit.SECONDS);
        // Calculate remainder separately to avoid overflow.
        long nano = now.getNano();
        long ft = (seconds * 10_000_000) + (nano / 100);
        Assertions.assertEquals(now, FiletimeUtils.toInstant(ft));
    }

    @Test
    void testNullInstantToFiletime() {
        Assertions.assertDoesNotThrow(() -> FiletimeUtils.fromInstant(null));
        Assertions.assertEquals(0L, FiletimeUtils.fromInstant(null));
    }
}
