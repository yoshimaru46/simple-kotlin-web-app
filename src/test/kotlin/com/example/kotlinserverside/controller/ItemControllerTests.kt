package com.example.kotlinserverside.controller

import com.example.kotlinserverside.entity.Item
import com.example.kotlinserverside.repository.ItemRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest
class ItemControllerTests(@Autowired private val webTestClient: WebTestClient) {
    @MockkBean
    lateinit var mockItemRepository: ItemRepository


    @DisplayName("Get all items")
    @Nested
    inner class GetList {
        private val getListUri = "/items"

        val testItem1 = Item(id = 1, name = "test1", price = 100)
        val testItem2 = Item(id = 2, name = "test2", price = 200)
        val testItems = listOf(testItem1, testItem2)

        @DisplayName("Success")
        @Test
        fun success() {
            every { mockItemRepository.findAll() } returns testItems

            val expectResponse = """
                [
                  {
                    "id": 1,
                    "name": "test1",
                    "price": 100
                  },
                  {
                    "id": 2,
                    "name": "test2",
                    "price": 200
                  }
                ]
            """.trimIndent()

            webTestClient.get().uri(getListUri).exchange()
                    .expectStatus().isOk
                    .expectBody().json(expectResponse)
            verify { mockItemRepository.findAll() }

        }
    }

}
