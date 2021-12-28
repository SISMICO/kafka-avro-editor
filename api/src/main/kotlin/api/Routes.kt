package api

import editor.Editor
import editor.KafkaSenderParser
import editor.Properties
import editor.jsonschema.JsonGenerator
import editor.schemaregistry.SchemaRegistry
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting(
    editor: Editor = Editor(Properties.outputPath),
    schema: SchemaRegistry = SchemaRegistry(),
    jsonGenerator: JsonGenerator = JsonGenerator(),
    sender: KafkaSenderParser = KafkaSenderParser()
) {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/topics") {
            call.respond(schema.getAllSchemas())
        }

        get("/json/{topic}") {
            val topic = editor.getTopic(call.parameters["topic"]!!)
            call.respond(jsonGenerator.generate(topic))
        }

        post("/send/{topic}") {
            val topic = editor.getTopic(call.parameters["topic"]!!)
            sender.send(topic, call.receiveText())
            call.respond(HttpStatusCode.OK, "OK")
        }
    }
}
