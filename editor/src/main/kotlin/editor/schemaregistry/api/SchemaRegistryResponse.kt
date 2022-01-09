package editor.schemaregistry.api

data class SchemaRegistryResponse(
    val subject: String,
    val version: Int,
    val id: Long,
    val schema: String
)
