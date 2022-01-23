package editor

class EditorParserException(message: String, ex: Exception) : Exception(message, ex.cause)
