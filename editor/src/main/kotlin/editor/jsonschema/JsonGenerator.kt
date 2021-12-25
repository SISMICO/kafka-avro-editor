package editor.jsonschema

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import editor.EditorTopic
import org.apache.avro.Schema
import org.apache.avro.specific.SpecificData
import org.apache.avro.specific.SpecificRecord
import uk.co.jemos.podam.api.PodamFactory
import uk.co.jemos.podam.api.PodamFactoryImpl
import java.io.File
import java.net.URL
import java.net.URLClassLoader

class JsonGenerator {

    fun generate(topic: EditorTopic): String {
        val factory: PodamFactory = PodamFactoryImpl()
        val myPojo = factory.manufacturePojoWithFullData(loadClass(topic))
        val mapper = ObjectMapper().registerKotlinModule()
        mapper.addMixIn(
            SpecificRecord::class.java,
            JacksonIgnoreAvroPropertiesMixIn::class.java
        )
        return mapper.writeValueAsString(myPojo)
    }

    private fun loadClass(topic: EditorTopic): Class<*> {
        val url = topic.classPath.toURI().toURL()
        val urls = arrayOf<URL>(url)
        val cl: ClassLoader = URLClassLoader(urls, ClassLoader.getSystemClassLoader())
        return cl.loadClass(topic.className)
    }

    private fun findClasses(javaClassesPath: String, extension: String): List<File> {
        val list = mutableListOf<File>()
        File(javaClassesPath).listFiles { file -> file.extension == extension && !file.name.contains("$") }
            .forEach { list.add(it) }
        File(javaClassesPath).listFiles { file -> file.isDirectory }
            .forEach { list.addAll(findClasses(it.path, extension)) }
        return list
    }
}

abstract class JacksonIgnoreAvroPropertiesMixIn {
    @get:JsonIgnore
    abstract val schema: Schema?

    @get:JsonIgnore
    abstract val specificData: SpecificData?
}
