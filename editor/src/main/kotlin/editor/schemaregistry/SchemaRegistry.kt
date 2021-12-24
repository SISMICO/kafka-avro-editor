package editor.schemaregistry

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.jackson.responseObject

class SchemaRegistry(
    val topicFinder: TopicFinder = TopicFinder()
) {

//    fun registerSchema(topic: String, schema: String) {
//
//    }
//
    fun getAllSchemas(): List<SubjectSchema> {
        return topicFinder.getAllTopics().map {
            SubjectSchema(it, getSchemaFromSchemaRegistry(it))
        }
    }

    fun getSchema(topic: String): SubjectSchema {
        val topics = getAllSchemas()
        return topics.filter { it.topic == topic }.getOrNull(0) ?: throw TopicNotFound(topic)
    }

    private fun getSchemaFromSchemaRegistry(topic: String): String {
        return "${Properties.schemaRegistryServer}/subjects/${topic}/versions/latest".httpGet()
            .responseObject<SchemaRegistryResponse>().third.get().schema
    }

    class TopicNotFound(topic: String) : Exception("Topic $topic Not Found")
}