package editor

import java.io.File
import javax.tools.DiagnosticCollector
import javax.tools.JavaCompiler
import javax.tools.JavaFileObject
import javax.tools.StandardJavaFileManager
import javax.tools.ToolProvider

class SchemaBuilder {

    fun buildSchemas(classes: List<File>) {
        classes.groupBy { it.parent }
            .forEach {
                build(it.value)
            }
    }

    private fun build(files: List<File>) {
        val compiler: JavaCompiler = ToolProvider.getSystemJavaCompiler()
        val fileManager: StandardJavaFileManager = compiler.getStandardFileManager(null, null, null)
        val compilationUnits = fileManager.getJavaFileObjectsFromFiles(files)
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
