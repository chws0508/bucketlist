package com.woosuk.domain.model

enum class AgeRange(val value: IntRange) {
    Teenage(10..19),
    Twenties(20..29),
    Thirties(30..39),
    Forties(40..49),
    Fifties(50..59),
    OldAge(60..Int.MAX_VALUE),
    UnSpecified(Int.MAX_VALUE..Int.MAX_VALUE),
}
