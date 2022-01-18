package editor

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File
import java.net.URL
import java.net.URLClassLoader

data class EditorTopic(
    val topic: String,
    val className: String,
    val classPath: File,
    val schemaPath: File
) {
    fun parse(message: String): Any {
        try {
            val objectClass = this.loadClass()
            val mapper = ObjectMapper()
            return mapper.readValue(message, objectClass)
        } catch (ex: Exception) {
            throw EditorParserException("Failed to parse message: $message", ex)
        }
    }

    fun loadClass(): Class<*> {
        val url = this.classPath.toURI().toURL()
        val urls = arrayOf<URL>(url)
        val cl: ClassLoader = URLClassLoader(urls, ClassLoader.getSystemClassLoader())
        return cl.loadClass(this.className)
    }
}
