package editor

import editor.compiler.EditorClassesLoader
import editor.compiler.SchemaBuilder
import editor.compiler.SchemaCompiler
import editor.schemaregistry.SchemaRegistry
import editor.schemaregistry.TopicNotFoundException
import java.io.File

class Editor(
    private val outputEditorPath: String,
    private val schemaRegistry: SchemaRegistry = SchemaRegistry(),
    private val compiler: SchemaCompiler = SchemaCompiler(),
    private val builder: SchemaBuilder = SchemaBuilder(),
    private val loader: EditorClassesLoader = EditorClassesLoader()
) {
    private var topics = mapOf<String, EditorTopic>()

    fun getTopic(topic: String): EditorTopic {
        buildSchema(topic)
        topics = loader.load(outputEditorPath)
        return topics[topic] ?: throw TopicNotFoundException(topic)
    }

    private fun listClasses(path: String): List<File> {
        val files = findClasses(path, "java")
        return files
    }

    private fun buildSchema(topic: String) {
        if (schemaRegistry.hasSubject(topic)) {
            val schema = schemaRegistry.getSchema(topic)
            val topicPath = "$outputEditorPath/$topic"
            compiler.compileSchema(schema, topicPath)
            builder.buildSchemas(listClasses(topicPath))
        }
    }

    private fun findClasses(javaClassesPath: String, extension: String): List<File> {
        val list = mutableListOf<File>()
        File(javaClassesPath).listFiles { file -> file.extension == extension && !file.name.contains("$") }
            .forEach { list.add(it) }
        File(javaClassesPath).listFiles { file -> file.isDirectory }
            .forEach { list.addAll(findClasses(it.path, extension)) }
        return list
    }
}
