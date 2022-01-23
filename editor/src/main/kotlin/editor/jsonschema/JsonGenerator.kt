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
        val myPojo = factory.manufacturePojoWithFullData(topic.loadClass())
        val mapper = ObjectMapper().registerKotlinModule()
        mapper.addMixIn(
            SpecificRecord::class.java,
            JacksonIgnoreAvroPropertiesMixIn::class.java
        )
        return mapper.writeValueAsString(myPojo)
    }
}

abstract class JacksonIgnoreAvroPropertiesMixIn {
    @get:JsonIgnore
    abstract val schema: Schema?

    @get:JsonIgnore
    abstract val specificData: SpecificData?
}
