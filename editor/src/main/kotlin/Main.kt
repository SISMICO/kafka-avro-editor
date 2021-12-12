import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.net.URLClassLoader


fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

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