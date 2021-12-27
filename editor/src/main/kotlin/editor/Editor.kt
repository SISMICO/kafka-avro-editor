package editor

import editor.schemaregistry.SchemaRegistry
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
        buildSchemas()
        topics = loader.load(outputEditorPath)
        return topics[topic]!!
    }

    private fun listClasses(): List<File> {
        return findClasses(outputEditorPath, "java")
    }

    private fun buildSchemas() {
        val schemas = schemaRegistry.getAllSchemas()
        schemas.forEach { compiler.compileSchema(it, "$outputEditorPath/${it.topic}") }
        builder.buildSchemas(listClasses())
    }

//    private fun loadTopics() {
//        findClasses(outputEditorPath, "class")
//            .forEach {
//                val topic = extractTopic(it.relativeTo(File(outputEditorPath)))
//                val className = extractClassName(topic, it.relativeTo(File(outputEditorPath)))
//                val classPath = File("$outputEditorPath/$topic")
//                val schemaPath = findClasses(classPath.path, "avsc")[0]
//                topics[topic] = EditorTopic(
//                    topic,
//                    className,
//                    classPath,
//                    schemaPath
//                )
//            }
//    }

    private fun findClasses(javaClassesPath: String, extension: String): List<File> {
        val list = mutableListOf<File>()
        File(javaClassesPath).listFiles { file -> file.extension == extension && !file.name.contains("$") }
            .forEach { list.add(it) }
        File(javaClassesPath).listFiles { file -> file.isDirectory }
            .forEach { list.addAll(findClasses(it.path, extension)) }
        return list
    }

//    private fun extractTopic(path: File): String {
//        var actual = path
//        while (actual.parent != null) {
//            actual = actual.parentFile
//        }
//        return actual.name
//    }
//    private fun extractClassName(topicName: String, path: File): String {
//        var actual = path
//        var className = path.nameWithoutExtension
//        while (actual.parentFile.name != topicName) {
//            actual = actual.parentFile
//            className = "${actual.name}.$className"
//        }
//        return className
//    }
}
