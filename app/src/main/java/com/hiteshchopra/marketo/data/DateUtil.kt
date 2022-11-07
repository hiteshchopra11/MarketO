package com.hiteshchopra.marketo.data

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.ISODateTimeFormat


object DateUtil {
    fun getAwsDate(): String {
        return DateTime(
            System.currentTimeMillis(), DateTimeZone.UTC
        ).toString(ISODateTimeFormat.basicDateTimeNoMillis())
    }
}