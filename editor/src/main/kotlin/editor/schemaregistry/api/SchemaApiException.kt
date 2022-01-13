package editor.schemaregistry.api

class SchemaApiException(message: String, ex: Exception) : Exception(message, ex.cause)
