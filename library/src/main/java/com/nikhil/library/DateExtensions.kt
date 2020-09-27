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