package pl.androidcoder.unittest

data class AmpereHour(val value: Double) {
    companion object {
        fun fromMicroAmpereHour(microAmperesHour: Int): AmpereHour {
            return AmpereHour(microAmperesHour / 1000000.0)
        }
    }
}