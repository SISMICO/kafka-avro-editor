import editor.EditorConsole
import editor.SchemaBuilder
import editor.SchemaCompiler
import editor.schemaregistry.SchemaRegistry
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutput
import java.io.ObjectOutputStream
import java.net.MalformedURLException
import java.net.URL
import java.net.URLClassLoader

fun main(args: Array<String>) {
    EditorConsole(args).run()
}

private fun buildSchemas() {
    val schemas = SchemaRegistry().getAllSchemas()
    val compiler = SchemaCompiler(Properties.outputAvscPath!!, Properties.outputJavaPath!!)
    val builder = SchemaBuilder()
    schemas.forEach { compiler.buildSchema(it) }
    builder.buildSchemas(Properties.outputJavaPath!!)
}

fun loadClass(): Any {
    var file = File("/tmp/kafka-avro-editor/schemas/build/")
    try {
        // Convert File to a URL
        val url = file.toURI().toURL()
        val urls = arrayOf<URL>(url)

        // Create a new class loader with the directory
        val cl: ClassLoader = URLClassLoader(urls, ClassLoader.getSystemClassLoader())

        val classToLoad = cl.loadClass("br.sismico.avro.Person")
        val setName = classToLoad.getDeclaredMethod("setName", String::class.java)
        val getName = classToLoad.getDeclaredMethod("getName")
        val setBithdate = classToLoad.getDeclaredMethod("setBirthDate", String::class.java)
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
        throw e
    } catch (e: ClassNotFoundException) {
        throw e
    }
}
