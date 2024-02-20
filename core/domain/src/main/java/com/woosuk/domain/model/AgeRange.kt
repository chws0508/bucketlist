package com.woosuk.domain.model

enum class AgeRange(val value: IntRange) {
    Twenties(20..29),
    Thirties(30..39),
    Forties(40..49),
    Fifties(50..59),
    OldAge(60..Int.MAX_VALUE),
    UnSpecified(0..0),
}
