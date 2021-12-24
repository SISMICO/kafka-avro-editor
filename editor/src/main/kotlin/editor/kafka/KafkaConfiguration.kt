package editor.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

class KafkaConfiguration {

    private var kafkaServer: String? = null
    private var schemaRegistryServer: String? = null

    init {
        this.loadSystemVariables()
    }

    fun createProducer(): KafkaProducer<Any, Any> {
        val props = Properties()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaServer
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = io.confluent.kafka.serializers.KafkaAvroSerializer::class.java
        props["schema.registry.url"] = schemaRegistryServer
        return KafkaProducer(props)
    }

    private fun loadSystemVariables() {
        val environments = System.getenv()
        kafkaServer = environments.getOrDefault(Constants.ENV_KAFKA_SERVER, "localhost:9092")
        schemaRegistryServer = environments.getOrDefault(Constants.ENV_SCHEMA_REGISTRY_SERVER, "http://localhost:8081")
    }

}