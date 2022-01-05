package editor.schemaregistry

import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.httpGet
import editor.Properties

class SchemaRegistryRequest {
    fun get(url: String): Request {
        val request = url.httpGet()
            .header(Headers.ACCEPT, "*/*")
        Properties.schemaRegistryUser?.let {
            request
                .authentication()
                .basic(Properties.schemaRegistryUser, Properties.schemaRegistryPassword.orEmpty())
        }
        return request
    }
}
