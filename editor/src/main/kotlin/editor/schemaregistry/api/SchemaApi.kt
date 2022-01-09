package editor.schemaregistry.api

import com.github.kittinunf.fuel.jackson.responseObject
import editor.Properties
import editor.schemaregistry.SchemaRegistryRequest

class SchemaApi(
    val request: SchemaRegistryRequest = SchemaRegistryRequest()
) {
    fun getSchemaFromSchemaRegistry(topic: String): String {
        return request
            .get("${Properties.schemaRegistryServer}/subjects/$topic-value/versions/latest")
            .responseObject<SchemaRegistryResponse>()
            .third
            .fold(
                success = { it.schema },
                failure = { ex -> throw SchemaApiException("Failed to get schemas: ${ex.message}", ex) }
            )
    }
}
