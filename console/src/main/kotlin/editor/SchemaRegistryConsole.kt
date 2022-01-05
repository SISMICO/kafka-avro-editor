package editor

import editor.schemaregistry.SchemaRegistry
import editor.schemaregistry.SubjectSchema
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option

class SchemaRegistryConsole(
    private val schemaRegistry: SchemaRegistry = SchemaRegistry()
) : EditorConsoleOption {

    private val optionListSubjects = Option("l", "list", false, "List all topics.")
    private val optionPrintSchema = Option.builder()
        .longOpt("schema")
        .hasArg()
        .argName("topic")
        .desc("Get schema from topic.")
        .build()
    override val options = listOf(
        optionListSubjects,
        optionPrintSchema
    )

    override fun run(commandLine: CommandLine) {
        if (commandLine.hasOption(optionListSubjects)) list()
        if (commandLine.hasOption(optionPrintSchema)) {
            val subject = commandLine.getOptionValue(optionPrintSchema)
            val subjectSchema = schemaRegistry.getSchema(subject)
            printSchema(subjectSchema)
        }
    }

    private fun list() {
        println("Listing Topics")
        SchemaRegistry().getAllSubjects().forEach {
            println(it)
        }
    }

    private fun printSchema(subjectSchema: SubjectSchema) =
        println("Schema for Topic: ${subjectSchema.topic}\n${subjectSchema.schema}")
}
