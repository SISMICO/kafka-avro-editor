package editor.kafka

import editor.EditorTopic
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.SerializationException
import java.net.URL
import java.net.URLClassLoader

class KafkaSender(
    val kafkaConfiguration: KafkaConfiguration = KafkaConfiguration()
) {

    fun send(topic: String, message: Any) {
        val producer = kafkaConfiguration.createProducer()
        val key = null
//    val pathSchema = "/home/leonardo/Projetos/kafka-avro-editor/schemas/simple.avsc"
//    val schema = Schema.Parser().parse(File(pathSchema))
//    val avroRecord: GenericRecord = GenericData.Record(schema)
//    avroRecord.put("name", "Viviane Reis")
//    avroRecord.put("birthDate", "1985-07-25")

        val record = ProducerRecord<Any, Any>(topic, key, message)
        try {
            producer.send(record)
        } catch (e: SerializationException) {
            // may need to do something with it
        } // When you're finished producing records, you can flush the producer to ensure it has all been written to Kafka and
        // then close the producer to free its resources.
        finally {
            producer.flush()
            producer.close()
        }
    }
}