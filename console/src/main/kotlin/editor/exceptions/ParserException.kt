package editor.exceptions

class ParserException(message: String, ex: Exception) : Exception(message, ex.cause)
