package com.woosuk.domain.model

sealed class AgeRange(val age: IntRange) {
    data object Twenties : AgeRange(20..29)

    data object Thirties : AgeRange(30..39)

    data object Forties : AgeRange(40..49)

    data object Fifties : AgeRange(50..59)

    data object OldAge : AgeRange(60..Int.MAX_VALUE)
}
