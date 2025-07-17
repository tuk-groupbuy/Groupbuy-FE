package com.tuk.tugether.util.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


fun String.toReadDateFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

    val partyDateFormatter = DateTimeFormatter.ofPattern("yy.MM.dd hh:mm")

    return LocalDateTime.parse(this, formatter).format(partyDateFormatter).toString()
}

fun String.toLocalDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy / MM / dd | HH:mm")
    return LocalDateTime.parse(this, formatter).toString()
}

fun String.toWriteViewingPartyDateFormat(): String {
    return this.split(" ").run {
        "20${this[0].replace(".", " / ")} | ${this[1].trim()}"
    }
}

fun String.toListViewingPartyDateFormat(): String{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

    val partyDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    return LocalDateTime.parse(this, formatter).format(partyDateFormatter).toString()
}

fun String.toTimeFormat(): String{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

    val partyDateFormatter = DateTimeFormatter.ofPattern("HH:mm")

    return LocalDateTime.parse(this, formatter).format(partyDateFormatter).toString()
}


@SuppressLint("SimpleDateFormat")
fun String.toCalendar(): Calendar {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date = sdf.parse(this)
    return Calendar.getInstance().apply {
        time = date!!
    }
}
