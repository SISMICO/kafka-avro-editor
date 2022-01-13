package api

import editor.Editor
import editor.Properties
import editor.jsonschema.JsonGenerator
import editor.kafka.KafkaSender
import editor.schemaregistry.SchemaRegistry
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.api(
    editor: Editor = Editor(Properties.outputPath),
    schema: SchemaRegistry = SchemaRegistry(),
    jsonGenerator: JsonGenerator = JsonGenerator(),
    sender: KafkaSender = KafkaSender()
) {

    routing {
        get("/") {
            call.respondText("Welcome to Kafka Avro Editor API ;)")
        }

        get("/topics") {
            call.respond(schema.getAllTopics())
        }

        get("/json/{topic}") {
            val topic = editor.getTopic(call.parameters["topic"]!!)
            call.respond(jsonGenerator.generate(topic))
        }

        post("/send/{topic}") {
            val topic = editor.getTopic(call.parameters["topic"]!!)
            sender.send(topic.topic, call.receiveText())
            call.respond(HttpStatusCode.OK, "OK")
        }
    }
}
