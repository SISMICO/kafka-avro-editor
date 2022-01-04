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
        val subjects = schemaRegistry.getAllSubjects()
        subjects.forEach {
            val schema = schemaRegistry.getSchema(it)
            compiler.compileSchema(schema, "$outputEditorPath/$it")
        }
        builder.buildSchemas(listClasses())
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
