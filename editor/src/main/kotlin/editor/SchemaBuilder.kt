package editor

import java.io.File
import javax.tools.*

class SchemaBuilder {

    fun buildSchemas(javaClassesPath: String) {
        val classes = findJavaClasses(javaClassesPath)
        classes.forEach { build(it) }
    }

    private fun findJavaClasses(javaClassesPath: String): List<File> {
        val list = mutableListOf<File>()
        File(javaClassesPath).listFiles { file -> file.extension == "java" }
            .forEach { list.add(it) }
        File(javaClassesPath).listFiles { file -> file.isDirectory }
            .forEach { list.addAll(findJavaClasses(it.path)) }
        return list
    }

    private fun build(file: File) {
        val compiler: JavaCompiler = ToolProvider.getSystemJavaCompiler()
        val fileManager: StandardJavaFileManager = compiler.getStandardFileManager(null, null, null)
        val compilationUnits = fileManager.getJavaFileObjectsFromFiles(listOf(file))
        val diagnostics = DiagnosticCollector<JavaFileObject>()
        val task: JavaCompiler.CompilationTask = compiler.getTask(
            null,
            fileManager,
            diagnostics,
            null,
            null,
            compilationUnits
        )

        task.call()
        for (diagnostic in diagnostics.diagnostics) System.out.format(
            "Error on line %d in %s%n",
            diagnostic.lineNumber,
            diagnostic.source
        )
        fileManager.close()
    }

}