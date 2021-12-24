object Properties {
    var kafkaServer: String? = null
    var schemaRegistryServer: String? = null
    var outputAvscPath: String? = null
    var outputJavaPath: String? = null

    init {
        loadSystemVariables()
    }

    private fun loadSystemVariables() {
        val environments = System.getenv()
        kafkaServer = environments.getOrDefault(Constants.ENV_KAFKA_SERVER, "localhost:9092")
        schemaRegistryServer = environments.getOrDefault(Constants.ENV_SCHEMA_REGISTRY_SERVER, "http://localhost:8081")
        outputAvscPath = environments.getOrDefault(Constants.ENV_OUTPUT_AVSC_PATH, "/tmp/kafka-avro-editor/avsc")
        outputJavaPath = environments.getOrDefault(Constants.ENV_OUTPUT_JAVA_PATH, "/tmp/kafka-avro-editor/class")
    }
}
