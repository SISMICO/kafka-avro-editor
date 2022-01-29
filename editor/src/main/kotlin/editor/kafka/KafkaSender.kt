package editor.kafka

import org.apache.kafka.clients.producer.ProducerRecord

class KafkaSender(
    val kafkaConfiguration: KafkaConfiguration = KafkaConfiguration()
) {

    fun send(topic: String, message: Any) {
        val producer = kafkaConfiguration.createProducer()
        val key = null

        val record = ProducerRecord<Any, Any>(topic, key, message)
        try {
            producer.send(record)
        } catch (e: Exception) {
            throw KafkaSenderException(e)
        } finally {
            producer.flush()
            producer.close()
        }
    }
}
