package schemaregistry.console

import schemaregistry.Schema
import schemaregistry.SchemaRegistry

class SchemaRegistryConsole(
    val schemaRegistry: SchemaRegistry = SchemaRegistry()
) {
    init {
        println(
            """
            Welcome to Schema Registry Utility
            
            --list          To List All Topics And Schemas
            --list {topic}  To List a Topic and his Schema
            
        """.trimIndent()
        )
    }

    fun readCommand(args: Array<String>) {
        val builder = SchemaRegistryConsoleBuilder()
        args.forEach { builder.parserCommand(it) }

        if (builder.isList) list(builder.topic)
    }

    private fun list(topic: String?) {
        println("Listing Schemas")

        if (topic != null)
            printSchema(schemaRegistry.getSchema(topic))
        else
            SchemaRegistry().getAllSchemas()
                .forEach {
                    printSchema(it)
                }
    }

    private fun printSchema(schema: Schema) =
        println("${schema.topic}: ${schema.schema}")

}