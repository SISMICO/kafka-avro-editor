package editor

import editor.schemaregistry.SchemaRegistry
import java.io.File

class Editor(
    val outputEditorPath: String,
    val schemaRegistry: SchemaRegistry = SchemaRegistry()
) {
    val topics = mutableMapOf<String, EditorTopic>()

    init {
        buildSchemas()
        loadTopics()
    }

    fun getTopic(topic: String): EditorTopic {
        return topics[topic]!!
    }

    fun listClasses(): List<File> {
        return findClasses(outputEditorPath, "java")
    }

    private fun buildSchemas() {
        val schemas = schemaRegistry.getAllSchemas()
        val compiler = SchemaCompiler(outputEditorPath)
        val builder = SchemaBuilder()
        schemas.forEach { compiler.compileSchema(it) }
        builder.buildSchemas(listClasses())
    }

    private fun loadTopics() {
        findClasses(outputEditorPath, "class")
            .forEach {
                val topic = extractTopic(it.relativeTo(File(outputEditorPath)))
                val className = extractClassName(topic, it.relativeTo(File(outputEditorPath)))
                val classPath = File("$outputEditorPath/$topic")
                val schemaPath = findClasses(classPath.path, "avsc")[0]
                topics[topic] = EditorTopic(
                    topic,
                    className,
                    classPath,
                    schemaPath
                )
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

    private fun extractTopic(path: File): String {
        var actual = path
        while (actual.parent != null) {
            actual = actual.parentFile
        }
        return actual.name
    }
    private fun extractClassName(topicName: String, path: File): String {
        var actual = path
        var className = path.nameWithoutExtension
        while (actual.parentFile.name != topicName) {
            actual = actual.parentFile
            className = "${actual.name}.$className"
        }
        return className
    }

}