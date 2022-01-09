package editor.schemaregistry.api

class SubjectApiException(message: String, ex: Exception) : Exception(message, ex.cause)
