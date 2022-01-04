package editor

import editor.jsonschema.JsonGenerator
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option

class JsonSchemaConsole(
    private val jsonGenerator: JsonGenerator = JsonGenerator(),
    private val editor: Editor = Editor(Properties.outputPath)
) : EditorConsoleOption {
    private val optionPrintJson =
        Option.builder()
            .option("j")
            .longOpt("json")
            .hasArg()
            .argName("topic")
            .desc("Create a json object from a topic.")
            .build()
    override val options = listOf(optionPrintJson)

    override fun run(commandLine: CommandLine) {
        if (commandLine.hasOption(optionPrintJson)) {
            val topic = commandLine.getOptionValue(optionPrintJson)
            val topicEditor = editor.getTopic(topic)
            print(topicEditor)
        }
    }

    private fun print(topic: EditorTopic) {
        println("Json Object for topic: ${topic.topic}")
        println(jsonGenerator.generate(topic))
    }
}
