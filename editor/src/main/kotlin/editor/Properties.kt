package editor

object Properties {
    val kafkaServer: String
    val kafkaSaslMechanism: String?
    val kafkaSaslJaasConfig: String?
    val kafkaSecurityProtocol: String?
    val schemaRegistryServer: String
    val schemaRegistryUser: String?
    val schemaRegistryPassword: String?
    val outputPath: String

    init {
        val environments = System.getenv()
        kafkaServer = environments.getOrDefault(Constants.ENV_KAFKA_SERVER, "localhost:9092")
        kafkaSaslMechanism = environments.getOrDefault(Constants.ENV_KAFKA_SASL_MECHANISM, null)
        kafkaSaslJaasConfig = environments.getOrDefault(Constants.ENV_KAFKA_SASL_JAAS_CONFIG, null)
        kafkaSecurityProtocol = environments.getOrDefault(Constants.ENV_KAFKA_SECURITY_PROTOCOL, null)
        schemaRegistryServer = environments.getOrDefault(Constants.ENV_SCHEMA_REGISTRY_SERVER, "http://localhost:8081")
        schemaRegistryUser = environments.getOrDefault(Constants.ENV_SCHEMA_REGISTRY_USER, null)
        schemaRegistryPassword = environments.getOrDefault(Constants.ENV_SCHEMA_REGISTRY_PASSWORD, null)
        outputPath = environments.getOrDefault(Constants.ENV_OUTPUT_PATH, "/tmp/kafka-avro-editor")
    }
}
