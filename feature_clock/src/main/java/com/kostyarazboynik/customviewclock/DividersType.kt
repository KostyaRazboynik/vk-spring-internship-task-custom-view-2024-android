package com.kostyarazboynik.customviewclock


enum class DividersType {
    TWELVE_MINUTES,
    FIFTEEN_MINUTES,
    TWENTY_MINUTES,
    THIRTY_MINUTES,
    HOUR_DIVIDERS,
    NO_DIVIDERS,
}

fun String.toDividersType(): DividersType =
    try {
        DividersType.valueOf(this.uppercase())
    } catch (e: IllegalArgumentException) {
        DividersType.TWELVE_MINUTES
    }
