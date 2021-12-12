import org.apache.avro.Schema
import org.apache.avro.compiler.specific.SpecificCompiler
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.net.URLClassLoader


fun main(args: Array<String>) {
    println("Program arguments: ${args.joinToString()}")

    loadJar()
    buildSchema()
}

fun buildSchema() {
    val pathSchema = "/home/leonardo/Projetos/kafka-avro-editor/schemas/simple.avsc"
    val pathOutput = "/home/leonardo/Projetos/kafka-avro-editor/schemas/build"
    try {
        val compiler = SpecificCompiler(Schema.Parser().parse(File(pathSchema)))
        compiler.compileToDestination(File(pathSchema), File(pathOutput))
    } catch (e: IOException) {
        throw e
    }
}

fun loadJar() {
    var file = File("/home/leonardo/Projetos/kafka-avro-editor/libexample/build/libs/libexample-1.0-SNAPSHOT.jar")
    try {
        // Convert File to a URL
        val url = file.toURI().toURL()
        val urls = arrayOf<URL>(url)

        // Create a new class loader with the directory
        val cl: ClassLoader = URLClassLoader(urls, ClassLoader.getSystemClassLoader())

        val classToLoad = Class.forName("calculator.Compute", true, cl);
        val method = classToLoad.getDeclaredMethod("version")
        val instance = classToLoad.getDeclaredConstructor().newInstance()
        val result = method.invoke(instance)

        println("Version: $result")

        println("Loaded")
    } catch (e: MalformedURLException) {
        throw  e
    } catch (e: ClassNotFoundException) {
        throw  e
    }
}