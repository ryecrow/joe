package io.ryecrow.joe.util

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

/**
 * Tests of utility functions of filetime
 *
 * @author ryecrow
 * @since 1.0
 */
internal class FiletimeUtilsTests {

    @Test
    fun testLongFiletimeToInstant() {
        // FILETIME is defined as "100 nanoseconds since the midnight of 1st January 1601"
        val epoch = LocalDateTime.of(1601, Month.JANUARY, 1, 0, 0, 0, 0)
            .atZone(ZoneOffset.UTC).toInstant()
        val now = Instant.now()
        val seconds = epoch.until(now, ChronoUnit.SECONDS)
        // Calculate remainder separately to avoid overflow.
        val nano = now.nano.toLong()
        val ft = seconds * 10000000 + nano / 100
        assertEquals(now, filetimeToInstant(ft))
    }

    @Test
    fun testNullInstantToFiletime() {
        val nullInstant: Instant? = null
        assertDoesNotThrow { nullInstant.toFiletime() }
        assertEquals(0L, nullInstant.toFiletime())
    }
}