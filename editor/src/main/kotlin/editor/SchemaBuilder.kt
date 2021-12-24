package editor

import java.io.File
import javax.tools.*

class SchemaBuilder {

    fun buildSchemas(classes: List<File>) {
        classes.forEach { build(it) }
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