package api

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class RoutesTest {
    @Test
    fun testRoot() {
        withTestApplication(Application::api) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Welcome to Kafka Avro Editor API ;)", response.content)
            }
        }
    }
}
