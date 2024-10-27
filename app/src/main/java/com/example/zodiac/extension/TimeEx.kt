package com.example.zodiac.extension

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

const val timeFormat1 = "MM-dd-yyyy"
const val timeFormat2 = "HH:mm"
const val timeFormat3 = "MM-dd-yyyy HH:mm"
const val timeFormat4 = "MM-dd"
const val timeFormat5 = "yyyy"
const val timeFormat6 = "MM/dd/yyyy"
const val timeFormat7 = "MM-dd-yyyy-HH-mm-ss"
const val timeFormat8 = "HH:mm:ss MM-dd-yyyy"

fun formatDateLocaleDMY(date: Long): String? {
    val dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault())
    return dateFormat.format(date)
}

fun formatTimeLocale(date: Long): String? {
    return formatTimeLocaleFormat(date)
}

fun formatTimeLocaleFormat(date: Long): String? {
    val dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault())
    return dateFormat.format(date)
}
fun Date.getFormattedHourMinute(): String {
    val hourMinuteFormat = SimpleDateFormat("h:mm", Locale.getDefault())
    return hourMinuteFormat.format(this)
}

fun Date.getFormattedDayOfWeek(): String {
    val dayOfWeekFormat = SimpleDateFormat("E", Locale.getDefault())
    return dayOfWeekFormat.format(this)
}

fun Date.getFormattedDayOfMonth(): String {
    val dayOfMonthFormat = SimpleDateFormat("dd", Locale.getDefault())
    return dayOfMonthFormat.format(this)
}
fun getDateTimeOfWeekFormat(
    milliSeconds: Long, timeFormat: String = "EE, dd-MM-yyyy, HH:mm aa"
): String {
    val formatter = SimpleDateFormat(timeFormat, Locale.getDefault())
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}

fun getDateTimeOfWeek(milliSeconds: Long, timeFormat: String = "EE, dd-MM-yyyy, HH:mm aa"): String {
    return getDateTimeOfWeekFormat(milliSeconds, timeFormat)
}

fun getTimeFromDuration(millis: Long): String {
    if (millis / (36000000) >= 1) return String.format(
        "%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toHours(millis),
        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(millis)
        ), // The change is in this line
        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(
                millis
            )
        )
    )

    return String.format(
        "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(millis)
        ), // The change is in this line
        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(
                millis
            )
        )
    )
}

fun getDateTimeFormat(milliSeconds: Long, timeFormat: String = "HH:mm MM-dd-yyyy"): String {
    val formatter = SimpleDateFormat(timeFormat, Locale.ENGLISH)
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}

fun getDateTime(
    milliSeconds: Long = System.currentTimeMillis(),
    timeFormat: String = "HH:mm MM-dd-yyyy"
): String {
    return getDateTimeFormat(milliSeconds, timeFormat)
}

fun formatDateLocale(date: Long): String? {
    val dateFormat =
        DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, Locale.getDefault())
    return dateFormat.format(date)
}


fun String.toMillisecond(
    format: String = timeFormat8,
    locale: Locale = Locale.getDefault()
): Long? =
    SimpleDateFormat(format, locale).parse(this)?.time

fun longToDate(milliSeconds: Long, timeFormat: String = "HH:mm MM-dd-yyyy"): Date {
    val formatter = SimpleDateFormat(timeFormat, Locale.ENGLISH)
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return calendar.time
}

fun getStartTimeOfDate(time: Long): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

fun getDurationMinSec(duration: Int): String {
    val min = (duration / (1000 * 60))
    val sec = (duration / 1000) % 60
    return String.format("%02d:%02d", min, sec)
}