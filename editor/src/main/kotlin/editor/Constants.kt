package editor

class Constants {
    companion object {
        const val ENV_KAFKA_SERVER = "KAFKA_SERVER"
        const val ENV_KAFKA_SASL_MECHANISM = "KAFKA_SASL_MECHANISM"
        const val ENV_KAFKA_SASL_JAAS_CONFIG = "KAFKA_SASL_JAAS_CONFIG"
        const val ENV_KAFKA_SECURITY_PROTOCOL = "KAFKA_SECURITY_PROTOCOL"
        const val ENV_SCHEMA_REGISTRY_SERVER = "SCHEMA_REGISTRY_SERVER"
        const val ENV_SCHEMA_REGISTRY_USER = "SCHEMA_REGISTRY_USER"
        const val ENV_SCHEMA_REGISTRY_PASSWORD = "SCHEMA_REGISTRY_PASSWORD"
        const val ENV_OUTPUT_PATH = "EDITOR_OUTPUT_PATH"
    }
}
