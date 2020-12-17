package io.ryecrow.joe.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.time.temporal.ChronoUnit

/* Gap between 1 Jan 1601 and 1 Jan 1970, in seconds */
private const val GAP_FROM_UNIX_EPOCH = -11644473600L
private val FILETIME_EPOCH = LocalDateTime.of(1601, Month.JANUARY, 1, 0, 0, 0, 0)
    .atZone(ZoneId.systemDefault()).toInstant()

fun filetimeToInstant(filetime: ByteArray): Instant {
    var time: Long = (filetime[filetime.size - 1].toInt() and 0xff).toLong()
    var tmp: Long
    for (index in filetime.size - 2 downTo 0) {
        time = time shl 8
        tmp = filetime[index].toLong() and 0xff
        time = time or tmp
    }
    return filetimeToInstant(time)
}

fun filetimeToInstant(filetime: Long): Instant {
    // Assume file must be created after 1 January 1601
    require(filetime >= 0) { "FILETIME must be positive" }
    val nano = filetime % 10000000 * 100
    val second = filetime / 10000000 + GAP_FROM_UNIX_EPOCH
    return Instant.ofEpochSecond(second, nano)
}

fun Instant?.toFiletime(): Long {
    return if (this == null) {
        return 0L
    } else {
        val seconds = FILETIME_EPOCH.until(this, ChronoUnit.SECONDS)
        val nano = this.nano.toLong()
        seconds * 10000000 + nano / 100
    }
}