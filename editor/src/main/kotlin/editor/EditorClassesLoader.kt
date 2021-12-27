package editor

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

class EditorClassesLoader {

    fun load(outputPath: String): Map<String, EditorTopic> {
        val topics = mutableMapOf<String, EditorTopic>()
        getTopics(outputPath)
            .forEach { (topic, topicPath) ->
                topics[topic] = EditorTopic(
                    topic = topic,
                    className = getClassName(getSchemaFile(topicPath)),
                    classPath = topicPath,
                    schemaPath = getSchemaFile(topicPath)
                )
            }
        return topics
    }

    private fun getTopics(outputPath: String) =
        File(outputPath)
            .listFiles()
            .map { Pair(it.name, it) }

    private fun getSchemaFile(topicPath: File) =
        topicPath
            .listFiles { filter -> filter.isFile }
            .first()

    private fun getClassName(schemaFile: File): String {
        val jsonObject = ObjectMapper()
            .registerKotlinModule()
            .readValue(schemaFile, SchemaJsonObject::class.java)
        return "${jsonObject.namespace}.${jsonObject.name}"
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private data class SchemaJsonObject(
        val name: String,
        val namespace: String
    )
}
