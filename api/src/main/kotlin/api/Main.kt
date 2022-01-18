package api

import api.configuration.configureSerialization
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(CORS) {
            anyHost()
        }
        api()
        configureSerialization()
    }.start(wait = true)
}
