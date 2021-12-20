package schemaregistry

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.jackson.responseObject

class SchemaRegistry(
    val topicFinder: TopicFinder = TopicFinder()
) {

//    fun registerSchema(topic: String, schema: String) {
//
//    }
//
    fun getAllSchemas(): List<Schema> {
        return topicFinder.getAllTopics().map {
            Schema(it, getSchemaFromSchemaRegistry(it))
        }
    }

    fun getSchema(topic: String): Schema {
        val topics = getAllSchemas()
        return topics.filter { it.topic == topic }.getOrNull(0) ?: throw TopicNotFound(topic)
    }

    private fun getSchemaFromSchemaRegistry(topic: String): String {
        return "${Properties.schemaRegistryServer}/subjects/${topic}/versions/latest".httpGet()
            .responseObject<SchemaRegistryResponse>().third.get().schema
    }

    class TopicNotFound(topic: String) : Exception("Topic $topic Not Found")
}