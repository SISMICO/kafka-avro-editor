package editor.compiler

class EditorParserException(message: String, ex: Exception) : Exception(message, ex.cause)
