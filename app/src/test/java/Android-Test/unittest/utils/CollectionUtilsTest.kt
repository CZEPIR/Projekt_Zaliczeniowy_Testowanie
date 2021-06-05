package pl.androidcoder.unittest.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class CollectionUtilsTest {
    @Test
    fun `given empty list removeDuplicates should return empty list`() {
        //ARRANGE
        val emptyList = emptyList<String>()
        //ACT
        val actualList = removeDuplicates(emptyList)
        //ASSERT
        assertEmptyList(actualList)
    }

    @Test
    fun `given list with nulls removeDuplicates should return empty list`() {
        //ARRANGE
        val emptyList = listOf<Any?>(null, null, null)
        //ACT
        val actualList = removeDuplicates(emptyList)
        //ASSERT
        assertEmptyList(actualList)
    }

    @Test
    fun `given list the same elements removeDuplicates should return list with one element`() {
        //ARRANGE
        val list = listOf(TestData("a"), TestData("a"), TestData("a"))
        val expectedList = listOf(TestData("a"))
        //ACT
        val actualList = removeDuplicates(list)
        //ASSERT
        assertEquals(expectedList, actualList)
    }

    @Test
    fun `given list with unique elements removeDuplicates should return the same list`() {
        //ARRANGE
        val expectedList = listOf(TestData("a"), TestData("b"), "first")
        //ACT
        val actualList = removeDuplicates(expectedList)
        //ASSERT
        assertEquals(expectedList, actualList)
    }

    @Test
    fun `given list with duplications removeDuplicates should return list without duplications`() {
        //ARRANGE
        val list = listOf(TestData("a"), TestData("a"), TestData("a"), "first", "first", "second")
        val expectedList = listOf(TestData("a"), "first", "second")
        //ACT
        val actualList = removeDuplicates(list)
        //ASSERT
        assertEquals(expectedList, actualList)
    }

    @Test
    fun `given list with duplications removeDuplicates should not edit given list`() {
        //ARRANGE
        val list = listOf(TestData("a"), TestData("a"), TestData("a"), "first", "first", "second")
        val expectedList = list.toList() //copy list
        //ACT
        removeDuplicates(list)
        //ASSERT
        assertEquals(expectedList, list)
    }

    private fun <T> assertEmptyList(actualList: List<T>) {
        assert(actualList.isEmpty()) { "List should be empty but has ${actualList.size} elements" }
    }

    data class TestData(val name: String)
}