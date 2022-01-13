package editor.schemaregistry

import editor.schemaregistry.api.SchemaApi
import editor.schemaregistry.api.SubjectApi

class SchemaRegistry(
    private val subjectApi: SubjectApi = SubjectApi(),
    private val schemaApi: SchemaApi = SchemaApi()
) {
    companion object {
        const val SUBJECT_SUFIX_LENGTH = 6
    }

    fun getAllTopics(): List<String> {
        return subjectApi
            .getAllSubjects()
            .map { extractTopicName(it) }
    }

    fun hasSubject(topic: String) =
        getAllTopics().contains(topic)

    fun getSchema(topic: String): SubjectSchema {
        val topics = getAllTopics()
        return if (topics.contains(topic))
            SubjectSchema(
                topic,
                schemaApi.getSchemaFromSchemaRegistry(topic)
            )
        else
            throw TopicNotFoundException(topic)
    }

    private fun extractTopicName(subject: String) =
        subject.substring(0, subject.length - SUBJECT_SUFIX_LENGTH)
}
