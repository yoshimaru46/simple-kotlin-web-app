package com.example.kotlinserverside.service

import com.example.kotlinserverside.entity.Item
import com.example.kotlinserverside.repository.ItemRepository

import io.mockk.every
import io.mockk.verify

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import com.ninjasquad.springmockk.MockkBean

@SpringBootTest
class ItemsServiceTests(@Autowired private val itemService: ItemService) {
    @MockkBean
    private lateinit var mockItemRepository: ItemRepository

    val testItem1 = Item(id = 1, name = "test1", price = 100)
    val testItem2 = Item(id = 2, name = "test2", price = 200)
    val testItems = listOf(testItem1, testItem2)

    @Test
    fun testGetItemsWithUpperCase() {
        every { mockItemRepository.findAll() } returns testItems

        val expectedItem1 = Item(id = 1, name = "TEST1", price = 100)
        val expectedItem2 = Item(id = 2, name = "TEST2", price = 200)
        val expectedItems = listOf(expectedItem1, expectedItem2)

        Assertions.assertEquals(expectedItems, itemService.getItemWithUpperCase())
        verify { mockItemRepository.findAll() }
    }
}
