package editor.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties

class KafkaConfiguration {
    private val kafkaServer: String
    private val schemaRegistryServer: String

    init {
        kafkaServer = editor.Properties.kafkaServer
        schemaRegistryServer = editor.Properties.schemaRegistryServer
    }

    fun createProducer(): KafkaProducer<Any, Any> {
        val props = Properties()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaServer
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
            io.confluent.kafka.serializers.KafkaAvroSerializer::class.java
        props["schema.registry.url"] = schemaRegistryServer
        return KafkaProducer(props)
    }
}
