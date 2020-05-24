package com.example.kotlinserverside.integration

import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup(
        Sql("classpath:/sql/create_table_items.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql("classpath:/sql/refresh_data_items.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
)
class ItemIntegrationTests(@Autowired private val webTestClient: WebTestClient) {
    private val getListUri = "/items"

    @Test
    fun testGetList() {
        val expectResponse = """
                [
                  {
                    "id": 1,
                    "name": "kiwi",
                    "price": 150
                  },
                  {
                    "id": 2,
                    "name": "cherry",
                    "price": 250
                  }
                ]
            """.trimIndent()

        webTestClient.get().uri(getListUri).exchange()
                .expectStatus().isOk
                .expectBody().json(expectResponse)

    }
}
