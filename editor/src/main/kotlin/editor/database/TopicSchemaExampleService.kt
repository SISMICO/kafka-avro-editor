package editor.database

import editor.database.entity.TopicSchema
import editor.database.entity.TopicsSchemas
import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.entity.filter
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class TopicSchemaExampleService(
    databaseService: DatabaseService
) {
    private var database: Database = databaseService.getInstance()

    fun getExamples(topic: String): List<TopicSchema> {
        val mylist = database.sequenceOf(TopicsSchemas).filter { it.topic eq topic }.toList()
        return mylist
    }

    fun addExample(topic: String, name: String, example: String) =
        database.insert(TopicsSchemas) {
            set(it.topic, topic)
            set(it.name, name)
            set(it.example, example)
        }

    fun removeExample(topic: String, name: String) =
        database.delete(TopicsSchemas) { (it.topic eq topic) and (it.name eq name) }
}
