package editor

import editor.jsonschema.JsonGenerator

class JsonSchemaConsole(
    private val jsonGenerator: JsonGenerator = JsonGenerator()
) {
    fun print(topic: EditorTopic) {
        println("Json Object for topic: ${topic.topic}")
        println(jsonGenerator.generate(topic))
    }
}
