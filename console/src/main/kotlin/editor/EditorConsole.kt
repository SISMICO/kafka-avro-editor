package editor

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options

class EditorConsole(
    private val args: Array<String>,
    private val schemaRegistry: SchemaRegistryConsole = SchemaRegistryConsole(),
    private val jsonSchema: JsonSchemaConsole = JsonSchemaConsole(),
    private val sender: KafkaSenderConsole = KafkaSenderConsole(),
    private val editor: Editor = Editor(Properties.outputPath)
) {

    private val options: Options
    private val optionList: Option
    private val optionJson: Option
    private val optionSend: Option
    private val cmd: CommandLine

    init {
        optionList = createOptionList()
        optionJson = createOptionJson()
        optionSend = createOptionSend()
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
            return
        }

        if (cmd.hasOption(optionSend)) {
            val topic = editor.getTopic(cmd.getOptionValue(optionSend))
            sender.send(topic)
            return
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
            addOption(optionSend)
        }

    private fun createOptionList() = Option("l", "list", false, "List All Topics And Schemas.")

    private fun createOptionJson() = Option.builder()
        .option("j")
        .longOpt("json")
        .hasArg()
        .argName("topic")
        .desc("Create a json object from a topic.")
        .build()

    private fun createOptionSend(): Option = Option.builder()
        .option("s")
        .longOpt("send")
        .hasArg()
        .argName("topic")
        .desc("Send a message to Kafka using Json. One message per line.")
        .build()
}
