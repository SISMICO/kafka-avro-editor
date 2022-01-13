package api

import api.configuration.configureSerialization
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        api()
        configureSerialization()
    }.start(wait = true)
}
