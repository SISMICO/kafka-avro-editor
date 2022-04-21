package editor.schemaregistry.api

import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import editor.Properties

class SchemaRegistryRequest {
    fun get(url: String): Request {
        val request = url.httpGet()
            .header(Headers.ACCEPT, "*/*")
        Properties.kafka.schemaRegistryUser?.let {
            request
                .authentication()
                .basic(Properties.kafka.schemaRegistryUser.orEmpty(), Properties.kafka.schemaRegistryPassword.orEmpty())
        }
        return request
    }
}
