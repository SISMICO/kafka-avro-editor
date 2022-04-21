package api

import api.configuration.configureSerialization
import editor.database.DatabaseService
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    DatabaseService().migrate()
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(CORS) {
            anyHost()
        }
        api()
        configureSerialization()
    }.start(wait = true)
}
