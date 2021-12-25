package editor.kafka

import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.SerializationException

class KafkaSender(
    val kafkaConfiguration: KafkaConfiguration = KafkaConfiguration()
) {

    fun send(topic: String, message: Any) {
        val producer = kafkaConfiguration.createProducer()
        val key = null

        val record = ProducerRecord<Any, Any>(topic, key, message)
        try {
            producer.send(record)
        } catch (e: SerializationException) {
            throw e
        } finally {
            producer.flush()
            producer.close()
        }
    }
}
