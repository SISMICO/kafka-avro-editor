package editor

import editor.schemaregistry.SchemaRegistry
import editor.schemaregistry.SubjectSchema

class SchemaRegistryConsole(
    private val schemaRegistry: SchemaRegistry = SchemaRegistry()
) {
    fun list(topic: String?) {
        println("Listing Schemas")

        if (topic != null)
            printSchema(schemaRegistry.getSchema(topic))
        else
            SchemaRegistry().getAllSchemas()
                .forEach {
                    printSchema(it)
                }
    }

    private fun printSchema(subjectSchema: SubjectSchema) =
        println("${subjectSchema.topic}: ${subjectSchema.schema}")
}
