package editor

object Properties {
    val kafka: KafkaProperties
    val database: DatabaseProperties
    val outputPath: String

    init {
        val environments = System.getenv()
        kafka = KafkaProperties
        database = DatabaseProperties
        outputPath = environments.getOrDefault(Constants.ENV_OUTPUT_PATH, "/tmp/kafka-avro-editor")
    }
}

object KafkaProperties {
    var kafkaServer: String
    var kafkaSaslMechanism: String?
    var kafkaSaslJaasConfig: String?
    var kafkaSecurityProtocol: String?
    var schemaRegistryServer: String
    var schemaRegistryUser: String?
    var schemaRegistryPassword: String?
    var outputPath: String

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

object DatabaseProperties {
    var engine: DatabaseEngine
    var url: String
    var driver: String?
    var user: String?
    var password: String?

    init {
        val environments = System.getenv()
        engine = DatabaseEngine.fromString(environments.getOrDefault(Constants.ENV_DATABASE_ENGINE, "sqlite3"))
        url = environments.getOrDefault(Constants.ENV_DATABASE_URL, "jdbc:sqlite:/tmp/editor.db")
        driver = environments.getOrDefault(Constants.ENV_DATABASE_DRIVER, "org.sqlite.JDBC")
        user = environments.getOrDefault(Constants.ENV_DATABASE_USER, "")
        password = environments.getOrDefault(Constants.ENV_DATABASE_PASSWORD, "")
    }
}

enum class DatabaseEngine(val engine: String) {
    PostgreSQL("postgresql"),
    SQLite3("sqlite3");

    companion object {
        fun fromString(value: String): DatabaseEngine =
            DatabaseEngine.values().first { it.engine == value }
    }
}
