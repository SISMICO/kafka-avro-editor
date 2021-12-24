package editor.schemaregistry.console

import editor.schemaregistry.SubjectSchema
import editor.schemaregistry.SchemaRegistry

class SchemaRegistryConsole(
    val schemaRegistry: SchemaRegistry = SchemaRegistry()
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