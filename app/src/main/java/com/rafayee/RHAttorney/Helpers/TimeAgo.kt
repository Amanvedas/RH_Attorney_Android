package com.rafayee.RHAttorney.Helpers

import android.annotation.SuppressLint
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimeAgo {
    fun covertTimeToText(dataDate: String): String? {
        var convTime: String? = null
        val prefix = ""
        val suffix = "ago"
        try {
            @SuppressLint("SimpleDateFormat") val dateFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val localTime = dateFormat.format(Date(dataDate.toLong()))
            val pasTime = dateFormat.parse(localTime)
            val nowTime = Date()
            assert(pasTime != null)
            val dateDiff = nowTime.time - pasTime!!.time
            val second = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hour = TimeUnit.MILLISECONDS.toHours(dateDiff)
            val day = TimeUnit.MILLISECONDS.toDays(dateDiff)
            convTime = if (dateDiff < second) {
                "just now"
            } else if (second < 60) {
                if (second == 1L) {
                    "$second second $suffix"
                } else {
                    "$second seconds $suffix"
                }
            } else if (minute < 60) {
                if (minute == 1L) {
                    "$minute minute $suffix"
                } else {
                    "$minute minutes $suffix"
                }
            } else if (hour < 24) {
                if (hour == 1L) {
                    "$hour hour $suffix"
                } else {
                    "$hour hours $suffix"
                }
            } else if (day >= 7) {
                if (day >= 365) {
                    val tempYear = day / 365
                    if (tempYear == 1L) {
                        "$tempYear year $suffix"
                    } else {
                        "$tempYear years $suffix"
                    }
                } else if (day >= 30) {
                    val tempMonth = day / 30
                    if (tempMonth == 1L) {
                        (day / 30).toString() + " month " + suffix
                    } else {
                        (day / 30).toString() + " months " + suffix
                    }
                } else {
                    val tempWeek = day / 7
                    if (tempWeek == 1L) {
                        (day / 7).toString() + " week " + suffix
                    } else {
                        (day / 7).toString() + " weeks " + suffix
                    }
                }
            } else {
                if (day == 1L) {
                    "$day day $suffix"
                } else {
                    "$day days $suffix"
                }
            }
            /* if (dateDiff < second) {
                convTime = "just now";
            }else if (second < 2) {
                convTime = second+" Second "+suffix;
            }else if (second < 60) {
                convTime = second+" Seconds "+suffix;
            } else if (minute < 2) {
                convTime = minute+" Minute "+suffix;
            }else if (minute < 60) {
                convTime = minute+" Minutes "+suffix;
            } else if (hour < 2) {
                convTime = hour+" Hour "+suffix;
            }else if (hour < 24) {
                convTime = hour+" Hours "+suffix;
            } else if (day >= 7) {
                if (day > 360) {
                    convTime = (day / 30) + " Years " + suffix;
                } else if (day > 30) {
                    convTime = (day / 360) + " Months " + suffix;
                } else {
                    convTime = (day / 7) + " Week " + suffix;
                }
            } else if (day < 2) {
                convTime = day+" Day "+suffix;
            }else if (day < 7) {
                convTime = day+" Days "+suffix;
            }*/
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("ConvTimeE", e.message.toString())
        }
        return convTime
    }
}