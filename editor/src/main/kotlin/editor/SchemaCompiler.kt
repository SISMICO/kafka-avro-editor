package editor

import editor.schemaregistry.SubjectSchema
import org.apache.avro.Schema
import org.apache.avro.compiler.specific.SpecificCompiler
import org.apache.avro.generic.GenericData
import java.io.File
import java.io.IOException

class SchemaCompiler(
    private val outputAvscPath: String,
    private val outputJavaPath: String
) {
    fun buildSchema(subjectSchema: SubjectSchema) {
        try {
            createDirectoryStructure()
            val compiler = SpecificCompiler(Schema.Parser().parse(subjectSchema.schema))
            compiler.setStringType(GenericData.StringType.String)
            compiler.compileToDestination(saveSchema(subjectSchema), File(outputJavaPath))
        } catch (e: IOException) {
            throw e
        }
    }

    private fun saveSchema(subjectSchema: SubjectSchema): File {
        val file = File("$outputAvscPath/${subjectSchema.topic}.avsc")
        file.writeText(subjectSchema.schema)
        return file
    }

    private fun createDirectoryStructure() {
        File(outputAvscPath).mkdirs()
        File(outputJavaPath).mkdirs()
    }
}