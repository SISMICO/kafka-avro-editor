package api

import editor.Editor
import editor.Properties
import editor.jsonschema.JsonGenerator
import editor.kafka.KafkaSender
import editor.schemaregistry.SchemaRegistry
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.defaultResource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing

data class ResponseOK(val status: String = "OK")

fun Application.api(
    editor: Editor = Editor(Properties.outputPath),
    schema: SchemaRegistry = SchemaRegistry(),
    jsonGenerator: JsonGenerator = JsonGenerator(),
    sender: KafkaSender = KafkaSender()
) {

    routing {
        get("/hello") {
            call.respondText("Welcome to Kafka Avro Editor API ;) ${System.getProperty("user.dir")}")
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
            sender.send(topic.topic, topic.parse(call.receiveText()))
            call.respond(HttpStatusCode.OK, ResponseOK())
        }

        static("/") {
            resources("web/.")
            defaultResource("web/index.html")
        }
    }
}
