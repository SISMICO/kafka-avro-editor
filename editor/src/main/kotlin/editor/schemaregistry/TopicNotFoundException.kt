package editor.schemaregistry

class TopicNotFoundException(topic: String) : Exception("Topic $topic Not Found")
