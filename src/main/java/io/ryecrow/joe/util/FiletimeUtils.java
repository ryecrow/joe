package io.ryecrow.joe.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for converting between Win32 FILETIME and {@link java.time.Instant}
 *
 * @author ryecrow
 * @since 1.0
 */
public final class FiletimeUtils {

    private static final long GAP_FROM_UNIX_EPOCH = -11644473600L;
    private static final Instant FILETIME_EPOCH = LocalDateTime.of(1601, Month.JANUARY, 1, 0, 0, 0, 0)
            .atZone(ZoneId.systemDefault()).toInstant();

    private FiletimeUtils() {
        throw new AssertionError("No instance of FiletimeUtils for you!");
    }

    public static Instant toInstant(byte[] filetime) {
        if (filetime == null) {
            throw new IllegalArgumentException("FILETIME must not be null");
        }
        long time = filetime[filetime.length - 1] & 0xff;
        long tmp;
        for (int index = filetime.length - 2; index >= 0; index--) {
            time = time << 8;
            tmp = (long) filetime[index] & 0xff;
            time |= tmp;
        }
        return toInstant(time);
    }

    public static Instant toInstant(long filetime) {
        // Assume file must be created after 1 January 1601
        if (filetime < 0) {
            throw new IllegalArgumentException("FILETIME must be positive");
        }
        long nano = (filetime % 10_000_000) * 100;
        long second = (filetime / 10_000_000) + GAP_FROM_UNIX_EPOCH;
        return Instant.ofEpochSecond(second, nano);
    }

    public static long fromInstant(Instant instant) {
        if (instant == null) {
            return 0L;
        }
        long seconds = FILETIME_EPOCH.until(instant, ChronoUnit.SECONDS);
        long nano = instant.getNano();
        return (seconds * 10_000_000) + (nano / 100);
    }
}
