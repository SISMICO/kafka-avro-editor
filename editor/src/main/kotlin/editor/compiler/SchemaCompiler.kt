package editor.compiler

import editor.schemaregistry.SubjectSchema
import org.apache.avro.Schema
import org.apache.avro.compiler.specific.SpecificCompiler
import org.apache.avro.generic.GenericData
import java.io.File
import java.io.IOException

class SchemaCompiler() {
    fun compileSchema(subjectSchema: SubjectSchema, outputPath: String) {
        try {
            createDirectoryStructure(outputPath)
            val compiler = SpecificCompiler(Schema.Parser().parse(subjectSchema.schema))
            compiler.setStringType(GenericData.StringType.String)
            compiler.compileToDestination(saveSchema(outputPath, subjectSchema), File(outputPath))
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
