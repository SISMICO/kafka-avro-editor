package editor

import editor.schemaregistry.SubjectSchema
import org.apache.avro.Schema
import org.apache.avro.compiler.specific.SpecificCompiler
import org.apache.avro.generic.GenericData
import java.io.File
import java.io.IOException

class SchemaCompiler(
    private val outputPath: String
) {
    fun compileSchema(subjectSchema: SubjectSchema) {
        try {
            val outputCompilerPath = "$outputPath/${subjectSchema.topic}"
            createDirectoryStructure(outputCompilerPath)
            val compiler = SpecificCompiler(Schema.Parser().parse(subjectSchema.schema))
            compiler.setStringType(GenericData.StringType.String)
            compiler.compileToDestination(saveSchema(outputCompilerPath, subjectSchema), File(outputCompilerPath))
        } catch (e: IOException) {
            throw e
        }
    }

    private fun saveSchema(outputPath: String, subjectSchema: SubjectSchema): File {
        val file = File("$outputPath/${subjectSchema.topic}.avsc")
        file.writeText(subjectSchema.schema)
        return file
    }

    private fun createDirectoryStructure(path: String) {
        File(path).mkdirs()
    }
}