package editor

import com.fasterxml.jackson.databind.ObjectMapper
import editor.kafka.KafkaSender
import java.net.URL
import java.net.URLClassLoader

class KafkaSenderParser(
    val sender: KafkaSender = KafkaSender()
) {

    fun send(topic: EditorTopic, message: String) {
        val messageObject = parseMessage(topic, message)
        sender.send(topic.topic, messageObject)
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
