package editor.schemaregistry

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.jackson.responseObject
import editor.Properties

class SchemaRegistry(
    val topicFinder: TopicFinder = TopicFinder()
) {
    fun getAllSubjects(): List<String> {
        return topicFinder
            .getAllTopics()
            .map { extractTopicName(it) }
    }

    fun hasSubject(topic: String) =
        getAllSubjects().contains(topic)

    fun getSchema(topic: String): SubjectSchema {
        val topics = getAllSubjects()
        return if (topics.contains(topic))
            SubjectSchema(
                topic,
                getSchemaFromSchemaRegistry(topic)
            )
        else
            throw TopicNotFound(topic)
    }

    private fun getSchemaFromSchemaRegistry(topic: String): String {
        return "${Properties.schemaRegistryServer}/subjects/$topic-value/versions/latest".httpGet()
            .responseObject<SchemaRegistryResponse>().third.get().schema
    }

    private fun extractTopicName(subject: String) =
        subject.substring(0, subject.length - 6)

    class TopicNotFound(topic: String) : Exception("Topic $topic Not Found")
}
