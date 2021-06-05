package pl.androidcoder.unittest

import android.os.BatteryManager

class BatteryInfoFactory(private val batteryManager: BatteryManager) {

    companion object {
        private const val DATA_NOT_AVAILABLE = Integer.MIN_VALUE
    }

    fun getBatteryCurrent(): BatteryCurrent? {
        val actualCurrent = getActualCurrent()
        val averageCurrent = getAverageCurrent()
        val actualCharge = getActualCharge()


        return if (allNull(actualCurrent, averageCurrent, actualCharge))
            null
        else
            BatteryCurrent(actualCurrent, averageCurrent, actualCharge)
    }

    private fun allNull(vararg variables: Any?) =
        variables.all { it == null }


    private fun getActualCharge(): AmpereHour? {
        val actualChargeProperty = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER)
        return if (actualChargeProperty != DATA_NOT_AVAILABLE)
            AmpereHour.fromMicroAmpereHour(actualChargeProperty)
        else
            null
    }

    private fun getAverageCurrent(): Ampere? {
        val currentAverageProperty = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE)
        return if (currentAverageProperty != DATA_NOT_AVAILABLE)
            Ampere.fromMicroAmpere(currentAverageProperty)
        else
            null
    }

    private fun getActualCurrent(): Ampere? {
        val currentNowProperty = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)
        return if (currentNowProperty != DATA_NOT_AVAILABLE)
            Ampere.fromMicroAmpere(currentNowProperty)
        else
            null
    }
}