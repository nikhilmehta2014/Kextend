package com.nikhil.library

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

//Example -> "Saturday, September 26, 2020 - 08:31:11 AM"
private const val TIME_STAMP_FORMAT = "EEEE, MMMM d, yyyy - hh:mm:ss a"
private const val DATE_FORMAT = "yyyy-MM-dd"

fun Long.getTimeStamp(): String {
    val date = Date(this)
    val simpleDateFormat = SimpleDateFormat(TIME_STAMP_FORMAT, Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getDefault()
    return simpleDateFormat.format(date)
}

fun Long.getYearMonthDay(): String {
    val date = Date(this)
    val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getDefault()
    return simpleDateFormat.format(date)
}

@Throws(ParseException::class)
fun String.getDateUnixTime(): Long {
    try {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getDefault()
        return simpleDateFormat.parse(this)!!.time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    throw ParseException("Please Enter a valid date", 0)
}

/**
 * Very often we get the date and time as a timestamp.
 * A timestamp is the number of seconds (sometimes milliseconds) since January 1, 1970.
 * This moment is called the epoch.
 */

/**
 * function to convert seconds into a [Date] object
 */
fun Int.toDate(): Date = Date(this.toLong() * 1000L)

/**
 * read-only property to convert seconds into a [Date] object
 */
val Int.asDate: Date
    get() = Date(this.toLong() * 1000L)

/**
 * Convert [String] to [Date] object.
 *
 * Performance warning:
 * In this example, we create [SimpleDateFormat] object every time.
 * If you use this extension inside a list or parse a big response from an API,
 * consider removing the `val dateFormatter = SimpleDateFormat(format, Locale.US)` from the extension code and putting it in as a global variable or a static class member.
 */
fun String.toDate(format: String): Date? {
    val dateFormatter = SimpleDateFormat(format, Locale.US)
    return try {
        dateFormatter.parse(this)
    } catch (e: ParseException) {
        null
    }
}

/**
 * Convert [Date] object to [String] format.
 */
fun Date.toString(format: String): String {
    val dateFormatter = SimpleDateFormat(format, Locale.US)
    return dateFormatter.format(this)
}