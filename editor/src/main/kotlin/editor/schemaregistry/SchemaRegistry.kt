package editor.schemaregistry

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.jackson.responseObject
import editor.Properties

class SchemaRegistry(
    val topicFinder: TopicFinder = TopicFinder()
) {
    fun getAllSchemas(): List<SubjectSchema> {
        return topicFinder.getAllTopics().map {
            SubjectSchema(
                extractTopicName(it),
                getSchemaFromSchemaRegistry(it)
            )
        }
    }

    fun getSchema(topic: String): SubjectSchema {
        val topics = getAllSchemas()
        return topics.filter { it.topic == topic }.getOrNull(0) ?: throw TopicNotFound(topic)
    }

    private fun getSchemaFromSchemaRegistry(topic: String): String {
        return "${Properties.schemaRegistryServer}/subjects/$topic/versions/latest".httpGet()
            .responseObject<SchemaRegistryResponse>().third.get().schema
    }

    private fun extractTopicName(subject: String) =
        subject.substring(0, subject.length - 6)

    class TopicNotFound(topic: String) : Exception("Topic $topic Not Found")
}
