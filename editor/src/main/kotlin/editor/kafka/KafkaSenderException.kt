package editor.kafka

class KafkaSenderException(ex: Exception) : Exception(ex.cause)
