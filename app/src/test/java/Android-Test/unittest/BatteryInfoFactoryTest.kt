package pl.androidcoder.unittest

import android.os.BatteryManager
import android.os.BatteryManager.*
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test

class BatteryInfoFactoryTest {

    companion object {
        private const val DATA_NOT_AVAILABLE = Integer.MIN_VALUE
    }

    private val batteryManager = mockk<BatteryManager>()
    private val factory = BatteryInfoFactory(batteryManager)

    @Test
    fun `when all data is unavailable then return null`() {
        //ARRANGE
        mockBatteryProperties(DATA_NOT_AVAILABLE, DATA_NOT_AVAILABLE, DATA_NOT_AVAILABLE)
        //ACT
        val batteryCurrent = factory.getBatteryCurrent()
        //ASSERT
        assertNull(batteryCurrent)
    }


    @Test
    fun `should return null actualCurrent when is not available`() {
        //ARRANGE
        mockBatteryProperties(DATA_NOT_AVAILABLE, 222222222, 333333333)
        //ACT
        val batteryCurrent = factory.getBatteryCurrent()
        //ASSERT
        assertBatteryCurrent(batteryCurrent, null, 222.222222, 333.333333)
    }

    @Test
    fun `should return null averageCurrent when is not available`() {
        //ARRANGE
        mockBatteryProperties(111111111, DATA_NOT_AVAILABLE, 333333333)
        //ACT
        val batteryCurrent = factory.getBatteryCurrent()
        //ASSERT
        assertBatteryCurrent(batteryCurrent, 111.111111, null, 333.333333)

    }

    @Test
    fun `should return nullss actualCharge when is not available`() {
        //ARRANGE
        mockBatteryProperties(111111111, 222222222, DATA_NOT_AVAILABLE)
        //ACT
        val batteryCurrent = factory.getBatteryCurrent()
        //ASSERT
        assertBatteryCurrent(batteryCurrent, 111.111111, 222.222222, null)
    }

    @Test
    fun `factory should deliver battery current in amperes`() {
        //ARRANGE
        mockBatteryProperties(111111111, 222222222, 333333333)
        //ACT
        val batteryCurrent = factory.getBatteryCurrent()
        //ASSERT
        assertBatteryCurrent(batteryCurrent, 111.111111, 222.222222, 333.333333)
    }

    private fun assertBatteryCurrent(
        batteryCurrent: BatteryCurrent?,
        expectedActualCurrent: Double?,
        expectedAverageCurrent: Double?,
        expectedActualCharge: Double?
    ) {
        assertNotNull(batteryCurrent)
        batteryCurrent?.apply {
            if (expectedActualCurrent != null)
                assertEquals(Ampere(expectedActualCurrent), actualCurrent)
            else
                assertNull(actualCurrent)

            if (expectedAverageCurrent != null)
                assertEquals(Ampere(expectedAverageCurrent), averageCurrent)
            else
                assertNull(averageCurrent)

            if (expectedActualCharge != null)
                assertEquals(AmpereHour(expectedActualCharge), actualCharge)
            else
                assertNull(actualCharge)
        }
    }

    private fun mockBatteryProperties(
        currentNowValueMicro: Int,
        currentAverageValueMicro: Int,
        chargeCounterValueMicro: Int
    ) {
        batteryManager.apply {
            every { getIntProperty(BATTERY_PROPERTY_CURRENT_NOW) } returns currentNowValueMicro
            every { getIntProperty(BATTERY_PROPERTY_CURRENT_AVERAGE) } returns currentAverageValueMicro
            every { getIntProperty(BATTERY_PROPERTY_CHARGE_COUNTER) } returns chargeCounterValueMicro
        }
    }
}