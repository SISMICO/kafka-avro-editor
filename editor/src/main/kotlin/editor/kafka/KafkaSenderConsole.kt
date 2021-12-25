package editor.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import editor.EditorTopic
import java.net.URL
import java.net.URLClassLoader

class KafkaSenderConsole(
    val sender: KafkaSender = KafkaSender()
) {

    fun send(topic: EditorTopic) {
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
        val objectClass = loadClass(topic)
        val mapper = ObjectMapper()
        return mapper.readValue(message, objectClass)
    }

    private fun loadClass(topic: EditorTopic): Class<*> {
        val url = topic.classPath.toURI().toURL()
        val urls = arrayOf<URL>(url)
        val cl: ClassLoader = URLClassLoader(urls, ClassLoader.getSystemClassLoader())
        return cl.loadClass(topic.className)
    }
}
