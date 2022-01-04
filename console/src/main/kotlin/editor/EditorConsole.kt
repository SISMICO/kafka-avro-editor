package editor

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options

class EditorConsole(
    private val args: Array<String>,
    private val schemaRegistry: SchemaRegistryConsole = SchemaRegistryConsole(),
    private val jsonSchema: JsonSchemaConsole = JsonSchemaConsole(),
    private val sender: KafkaSenderConsole = KafkaSenderConsole(),
    private val editor: Editor = Editor(Properties.outputPath)
) {
    private val cmd: CommandLine
    private val myOptions = mutableListOf<EditorConsoleOption>()
    private val options = Options()

    init {
        myOptions.addAll(
            listOf(
                schemaRegistry,
                jsonSchema,
                sender
            )
        )
        myOptions.flatMap { it.options }
            .forEach { options.addOption(it) }
        cmd = configureCommandLine(args)
    }

    fun run() {
        if (args.isEmpty()) {
            printHelper(options)
            return
        }

        myOptions.forEach {
            it.run(cmd)
        }
    }

    private fun configureCommandLine(args: Array<String>): CommandLine {
        val parser = DefaultParser()
        return parser.parse(options, args)
    }

    private fun printHelper(options: Options) {
        val formatter = HelpFormatter()
        formatter.printHelp("schema-editor", options)
    }
}
