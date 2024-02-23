package com.woosuk.testing

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

fun getTestLocalDateTime(
    year: Int = 2024,
    month: Int = 2,
    dayOfMoth: Int = 20,
    hour: Int = 10,
    minute: Int = 30,
) = LocalDateTime.of(
    LocalDate.of(year, month, dayOfMoth),
    LocalTime.of(hour, minute),
)
