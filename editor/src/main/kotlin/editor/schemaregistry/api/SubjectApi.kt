package editor.schemaregistry.api

import com.github.kittinunf.fuel.jackson.responseObject
import editor.Properties

class SubjectApi(
    private val request: SchemaRegistryRequest = SchemaRegistryRequest()
) {
    fun getAllSubjects(): List<String> {
        return request
            .get("${Properties.schemaRegistryServer}/subjects")
            .responseObject<List<String>>()
            .third
            .fold(
                success = { it },
                failure = { ex -> throw SubjectApiException("Failed to get schemas: ${ex.message}", ex) }
            )
    }
}
