package editor

import editor.jsonschema.JsonSchemaConsole
import editor.schemaregistry.console.SchemaRegistryConsole
import org.apache.commons.cli.*

class EditorConsole(
    private val args: Array<String>,
    private val schemaRegistry: SchemaRegistryConsole = SchemaRegistryConsole(),
    private val jsonSchema: JsonSchemaConsole = JsonSchemaConsole(),
    private val editor: Editor = Editor(Properties.outputPath)
) {

    private val options: Options
    private val optionList: Option
    private val optionJson: Option
    private val cmd: CommandLine

    init {
        optionList = createOptionList()
        optionJson = createOptionJson()
        options = createOptions()
        cmd = configureCommandLine(options, args)
    }

    fun run() {
        if (args.isEmpty()) {
            printHelper(options)
            return
        }

        if (cmd.hasOption(optionList)) {
            schemaRegistry.list(null)
            return
        }

        if (cmd.hasOption(optionJson)) {
            val topic = editor.getTopic(cmd.getOptionValue(optionJson))
            jsonSchema.print(topic)
        }
    }

    private fun configureCommandLine(options: Options, args: Array<String>): CommandLine {
        val parser = DefaultParser()
        return parser.parse(options, args)
    }

    private fun printHelper(options: Options) {
        val formatter = HelpFormatter()
        formatter.printHelp("schema-editor", options)
    }

    private fun createOptions() =
        Options().apply {
            addOption(optionList)
            addOption(optionJson)
        }

    private fun createOptionList() = Option("l", "list", false, "List All Topics And Schemas")

    private fun createOptionJson() = Option.builder()
        .option("j")
        .longOpt("json")
        .hasArg()
        .argName("topic")
        .desc("Create a json object from a topic")
        .build()
}