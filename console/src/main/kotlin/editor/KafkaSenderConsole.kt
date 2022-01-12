package editor

import com.fasterxml.jackson.databind.ObjectMapper
import editor.exceptions.ParserException
import editor.kafka.KafkaSender
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option
import java.net.URL
import java.net.URLClassLoader

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
        var message: String? = null
        do {
            println("Insert a Json Message:")
            message = readMessage()
            if (!message.isNullOrEmpty()) {
                val messageObject = parseMessage(topic, message)
                sender.send(topic.topic, messageObject)
            }
        } while (!message.isNullOrEmpty())
    }

    private fun readMessage(): String? {
        return readLine()
    }

    private fun parseMessage(topic: EditorTopic, message: String): Any {
        try {
            val objectClass = loadClass(topic)
            val mapper = ObjectMapper()
            return mapper.readValue(message, objectClass)
        } catch (ex: Exception) {
            throw ParserException("Failed to parse message: $message", ex)
        }
    }

    private fun loadClass(topic: EditorTopic): Class<*> {
        val url = topic.classPath.toURI().toURL()
        val urls = arrayOf<URL>(url)
        val cl: ClassLoader = URLClassLoader(urls, ClassLoader.getSystemClassLoader())
        return cl.loadClass(topic.className)
    }
}
