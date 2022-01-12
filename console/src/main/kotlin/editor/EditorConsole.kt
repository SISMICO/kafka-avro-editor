package editor

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class EditorConsole(
    private val args: Array<String>,
    schemaRegistry: SchemaRegistryConsole = SchemaRegistryConsole(),
    jsonSchema: JsonSchemaConsole = JsonSchemaConsole(),
    sender: KafkaSenderConsole = KafkaSenderConsole(),
) {
    private val cmd: CommandLine
    private val myOptions = mutableListOf<EditorConsoleOption>()
    private val options = Options()
    private var logger: Logger = LoggerFactory.getLogger(EditorConsole::class.java)

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

        try {
            myOptions.forEach {
                it.run(cmd)
            }
        } catch (ex: Exception) {
            logger.error("Ops, we got an error: ${ex.message}")
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
