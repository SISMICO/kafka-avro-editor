package editor.schemaregistry

data class SubjectSchema(
    val topic: String,
    val schema: String
)

data class SchemaRegistryResponse(
    val subject: String,
    val version: Int,
    val id: Long,
    val schema: String
)