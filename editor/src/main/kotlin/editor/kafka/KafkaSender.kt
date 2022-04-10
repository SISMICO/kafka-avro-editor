package editor.kafka

import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class KafkaSender(
    val kafkaConfiguration: KafkaConfiguration = KafkaConfiguration()
) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun send(topic: String, message: Any) {
        logger.info("Sending message for topic $topic")
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
