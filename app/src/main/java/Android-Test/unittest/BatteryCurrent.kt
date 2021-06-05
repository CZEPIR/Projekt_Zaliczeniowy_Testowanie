package pl.androidcoder.unittest

data class BatteryCurrent(
    val actualCurrent: Ampere?,
    val averageCurrent: Ampere?,
    val actualCharge: AmpereHour?
)