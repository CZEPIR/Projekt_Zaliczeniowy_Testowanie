package pl.androidcoder.unittest

data class Ampere(val value: Double) {
    companion object {
        fun fromMicroAmpere(microAmperes: Int): Ampere {
            return Ampere(microAmperes / 1000000.0)
        }
    }
}