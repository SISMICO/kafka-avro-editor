object Properties {
    var kafkaServer: String? = null
    var schemaRegistryServer: String? = null

    init {
        loadSystemVariables()
    }

    private fun loadSystemVariables() {
        val environments = System.getenv()
        kafkaServer = environments.getOrDefault(Constants.ENV_KAFKA_SERVER, "localhost:9092")
        schemaRegistryServer = environments.getOrDefault(Constants.ENV_SCHEMA_REGISTRY_SERVER, "http://localhost:8081")
    }
}