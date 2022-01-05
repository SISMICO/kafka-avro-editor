package editor.schemaregistry

import com.github.kittinunf.fuel.jackson.responseObject
import editor.Properties

class TopicFinder(
    val request: SchemaRegistryRequest = SchemaRegistryRequest()
) {
    fun getAllTopics(): List<String> {
        return request
            .get("${Properties.schemaRegistryServer}/subjects")
            .responseObject<List<String>>().third.get()
    }
}
