package editor

object Properties {
    val kafkaServer: String
    val schemaRegistryServer: String
    val outputPath: String

    init {
        val environments = System.getenv()
        kafkaServer = environments.getOrDefault(Constants.ENV_KAFKA_SERVER, "localhost:9092")
        schemaRegistryServer = environments.getOrDefault(Constants.ENV_SCHEMA_REGISTRY_SERVER, "http://localhost:8081")
        outputPath = environments.getOrDefault(Constants.ENV_OUTPUT_PATH, "/tmp/kafka-avro-editor")
    }
}
