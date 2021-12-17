import org.apache.avro.Schema
import org.apache.avro.compiler.specific.SpecificCompiler
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.StringSerializer
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.net.URLClassLoader
import java.util.*
import javax.tools.*


fun main(args: Array<String>) {
    println("Program arguments: ${args.joinToString()}")

    buildSchema()
    compileSchema()
    val myPerson = loadClass()
    sendEvent(myPerson)
    FromSchema().generateAvro("/home/leonardo/Projetos/kafka-avro-editor/schemas/simple.avsc")
}

fun sendEvent(event: Any) {
    val props = Properties()
    props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
    props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = io.confluent.kafka.serializers.KafkaAvroSerializer::class.java
    props["schema.registry.url"] = "http://localhost:8081"
    val producer = KafkaProducer<Any, Any>(props)

    val key = "key1"
//    val pathSchema = "/home/leonardo/Projetos/kafka-avro-editor/schemas/simple.avsc"
//    val schema = Schema.Parser().parse(File(pathSchema))
//    val avroRecord: GenericRecord = GenericData.Record(schema)
//    avroRecord.put("name", "Viviane Reis")
//    avroRecord.put("birthDate", "1985-07-25")

    val record = ProducerRecord<Any, Any>("topic1", key, event)
    try {
        producer.send(record)
    } catch (e: SerializationException) {
        // may need to do something with it
    } // When you're finished producing records, you can flush the producer to ensure it has all been written to Kafka and
    // then close the producer to free its resources.
    finally {
        producer.flush()
        producer.close()
    }
}

fun buildSchema() {
    val pathSchema = "/home/leonardo/Projetos/kafka-avro-editor/schemas/simple.avsc"
    val pathOutput = "/tmp/kafka-avro-editor/schemas/build"
    try {
        val compiler = SpecificCompiler(Schema.Parser().parse(File(pathSchema)))
        compiler.compileToDestination(File(pathSchema), File(pathOutput))
    } catch (e: IOException) {
        throw e
    }
}

fun compileSchema() {
    val pathJavaClass = "/tmp/kafka-avro-editor/schemas/build/br/sismico/avro/Person.java"
    val javaFile = File(pathJavaClass)
    val compiler: JavaCompiler = ToolProvider.getSystemJavaCompiler()
    // Get the file system manager of the compiler
    // Get the file system manager of the compiler
    val fileManager: StandardJavaFileManager = compiler.getStandardFileManager(null, null, null)
    // Create a compilation unit (files)
    // Create a compilation unit (files)
    val compilationUnits = fileManager.getJavaFileObjectsFromFiles(listOf(javaFile))
    // A feedback object (diagnostic) to get errors
    // A feedback object (diagnostic) to get errors
    val diagnostics = DiagnosticCollector<JavaFileObject>()
    // Compilation unit can be created and called only once
    // Compilation unit can be created and called only once
    val task: JavaCompiler.CompilationTask = compiler.getTask(
        null,
        fileManager,
        diagnostics,
        null,
        null,
        compilationUnits
    )
    // The compile task is called
    // The compile task is called
    task.call()
    // Printing of any compile problems
    // Printing of any compile problems
    for (diagnostic in diagnostics.diagnostics) System.out.format(
        "Error on line %d in %s%n",
        diagnostic.lineNumber,
        diagnostic.source
    )
    fileManager.close()
}

fun loadClass(): Any {
    var file = File("/tmp/kafka-avro-editor/schemas/build/")
    //var file = File("/tmp/kafka-avro-editor/schemas/build/")
    try {
        // Convert File to a URL
        val url = file.toURI().toURL()
        val urls = arrayOf<URL>(url)

        // Create a new class loader with the directory
        val cl: ClassLoader = URLClassLoader(urls, ClassLoader.getSystemClassLoader())

        //val classToLoad = Class.forName("Person", true, cl);
        val classToLoad = cl.loadClass("br.sismico.avro.Person");
        val setName = classToLoad.getDeclaredMethod("setName", CharSequence::class.java)
        val getName = classToLoad.getDeclaredMethod("getName")
        val setBithdate = classToLoad.getDeclaredMethod("setBirthDate", CharSequence::class.java)
        val writer = classToLoad.getDeclaredMethod("writeExternal", ObjectOutput::class.java)
        val instance = classToLoad.getDeclaredConstructor().newInstance()

        setName.invoke(instance, "Leonardo Bento")
        setBithdate.invoke(instance, "1985-02-07")
        val getResult = getName.invoke(instance)

        println("Name: $getResult")

        val file = FileOutputStream("/tmp/kafka-avro-editor/schemas/build/br/sismico/avro/Person.avro")
        val outputStream = ObjectOutputStream(file)
        writer.invoke(instance, outputStream)
        outputStream.flush()
        outputStream.close()
        file.close()

        println("Loaded")

        return instance
    } catch (e: MalformedURLException) {
        throw  e
    } catch (e: ClassNotFoundException) {
        throw  e
    }
}