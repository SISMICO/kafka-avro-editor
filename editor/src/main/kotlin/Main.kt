import org.apache.avro.Schema
import org.apache.avro.compiler.specific.SpecificCompiler
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.net.URLClassLoader
import java.util.*
import javax.tools.*


fun main(args: Array<String>) {
    println("Program arguments: ${args.joinToString()}")

    buildSchema()
    compileSchema()
    loadClass()
    //FromSchema().generateAvro("/home/leonardo/Projetos/kafka-avro-editor/schemas/simple.avsc")
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

fun loadClass() {
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
        val instance = classToLoad.getDeclaredConstructor().newInstance()

        val setResult = setName.invoke(instance, "Bento")
        val getResult = getName.invoke(instance)

        println("Name: $getResult")

        println("Loaded")
    } catch (e: MalformedURLException) {
        throw  e
    } catch (e: ClassNotFoundException) {
        throw  e
    }
}