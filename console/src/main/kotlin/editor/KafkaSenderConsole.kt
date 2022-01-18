package editor

import editor.kafka.KafkaSender
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option

class KafkaSenderConsole(
    val sender: KafkaSender = KafkaSender(),
    private val editor: Editor = Editor(Properties.outputPath)
) : EditorConsoleOption {
    private val optionSender =
        Option.builder()
            .option("s")
            .longOpt("send")
            .hasArg()
            .argName("topic")
            .desc("Send a message to Kafka using Json. One message per line.")
            .build()
    override val options = listOf(optionSender)

    override fun run(commandLine: CommandLine) {
        if (commandLine.hasOption(optionSender)) {
            val topic = commandLine.getOptionValue(optionSender)
            val topicEditor = editor.getTopic(topic)
            send(topicEditor)
        }
    }

    private fun send(topic: EditorTopic) {
        var message: String?
        do {
            println("Insert a Json Message:")
            message = readMessage()
            if (!message.isNullOrEmpty()) {
                val messageObject = topic.parse(message)
                sender.send(topic.topic, messageObject)
            }
        } while (!message.isNullOrEmpty())
    }

    private fun readMessage(): String? {
        return readLine()
    }
}
