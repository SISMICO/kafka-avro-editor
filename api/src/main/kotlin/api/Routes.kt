package api

import api.entity.ExampleRequest
import editor.Editor
import editor.Properties
import editor.database.DatabaseService
import editor.database.TopicSchemaExampleService
import editor.jsonschema.JsonGenerator
import editor.kafka.KafkaSender
import editor.schemaregistry.SchemaRegistry
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.defaultResource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.receive
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.routing

data class ResponseOK(val status: String = "OK")

fun Application.api(
    editor: Editor = Editor(Properties.outputPath),
    schema: SchemaRegistry = SchemaRegistry(),
    jsonGenerator: JsonGenerator = JsonGenerator(),
    sender: KafkaSender = KafkaSender(),
    examples: TopicSchemaExampleService = TopicSchemaExampleService(DatabaseService())
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

        get("/examples/{topic}") {
            val topic = call.parameters["topic"]!!
            call.respond(examples.getExamples(topic))
        }

        put("/examples") {
            val example = call.receive<ExampleRequest>()
            call.respond(examples.addExample(example.topic, example.name, example.example))
        }

        delete("/examples/{topic}/{name}") {
            val topic = call.parameters["topic"]!!
            val name = call.parameters["name"]!!
            call.respond(examples.removeExample(topic, name))
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
