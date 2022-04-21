package editor.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties

class KafkaConfiguration {
    private val kafkaServer: String
    private val schemaRegistryServer: String
    private val schemaRegistryUser: String?
    private val schemaRegistryPassword: String?
    private val kafkaSaslMechanism: String?
    private val kafkaSecurityProtocol: String?
    private val kafkaSaslJaasConfig: String?

    init {
        kafkaServer = editor.Properties.kafka.kafkaServer
        schemaRegistryServer = editor.Properties.kafka.schemaRegistryServer
        schemaRegistryUser = editor.Properties.kafka.schemaRegistryUser
        schemaRegistryPassword = editor.Properties.kafka.schemaRegistryPassword
        kafkaSaslMechanism = editor.Properties.kafka.kafkaSaslMechanism
        kafkaSecurityProtocol = editor.Properties.kafka.kafkaSecurityProtocol
        kafkaSaslJaasConfig = editor.Properties.kafka.kafkaSaslJaasConfig
    }

    fun createProducer(): KafkaProducer<Any, Any> {
        val props = Properties()
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] =
            io.confluent.kafka.serializers.KafkaAvroSerializer::class.java
        configureKafkaServer(props)
        configureSchemaRegistry(props)
        return KafkaProducer(props)
    }

    private fun configureSchemaRegistry(props: Properties) {
        props["schema.registry.url"] = schemaRegistryServer
        schemaRegistryUser?.let {
            props["basic.auth.credentials.source"] = "USER_INFO"
            props["schema.registry.basic.auth.user.info"] = "$schemaRegistryUser:$schemaRegistryPassword"
        }
    }

    private fun configureKafkaServer(props: Properties) {
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaServer
        kafkaSaslMechanism?.let { props["sasl.mechanism"] = kafkaSaslMechanism }
        kafkaSecurityProtocol?.let { props["security.protocol"] = kafkaSecurityProtocol }
        kafkaSaslJaasConfig?.let { props["sasl.jaas.config"] = kafkaSaslJaasConfig }
    }
}
