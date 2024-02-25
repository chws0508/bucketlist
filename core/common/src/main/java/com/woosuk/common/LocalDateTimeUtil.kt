package com.woosuk.common

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object LocalDateTimeUtil {
    fun Long.convertMillsToDateTime(): LocalDateTime =
        Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
}

