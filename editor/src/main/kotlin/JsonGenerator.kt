import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.avro.Schema
import org.apache.avro.specific.SpecificData
import org.apache.avro.specific.SpecificRecord
import uk.co.jemos.podam.api.PodamFactory
import uk.co.jemos.podam.api.PodamFactoryImpl
import java.io.File
import java.net.URL
import java.net.URLClassLoader


class JsonGenerator {

    fun generate(): String {
        val factory: PodamFactory = PodamFactoryImpl()
        val myPojo = factory.manufacturePojo(loadClass())
        val mapper = ObjectMapper()
        mapper.addMixIn(
            SpecificRecord::class.java,  // Interface implemented by all generated Avro-Classes
            JacksonIgnoreAvroPropertiesMixIn::class.java
        )
        return mapper.writeValueAsString(myPojo)
    }

    fun loadClass(): Class<*> {
        var file = File("/tmp/kafka-avro-editor/schemas/build/")
        //var file = File("/tmp/kafka-avro-editor/schemas/build/")
        // Convert File to a URL
        val url = file.toURI().toURL()
        val urls = arrayOf<URL>(url)

        // Create a new class loader with the directory
        val cl: ClassLoader = URLClassLoader(urls, ClassLoader.getSystemClassLoader())

        //val classToLoad = Class.forName("Person", true, cl);
        return cl.loadClass("br.sismico.avro.Person");
    }

}

abstract class JacksonIgnoreAvroPropertiesMixIn {
    @get:JsonIgnore
    abstract val schema: Schema?

    @get:JsonIgnore
    abstract val specificData: SpecificData?
}