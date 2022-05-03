package api

import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class RoutesTest {
    @Test
    fun testRoot() {
        withTestApplication(Application::api) {
            handleRequest(HttpMethod.Get, "/hello").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Welcome to Kafka Avro Editor API ;)", response.content)
            }
        }
    }
}
