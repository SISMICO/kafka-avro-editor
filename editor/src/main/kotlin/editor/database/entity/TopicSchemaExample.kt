package editor.database.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.ktorm.entity.Entity
import org.ktorm.jackson.json
import org.ktorm.schema.Table
import org.ktorm.schema.varchar

@JsonIgnoreProperties(value = ["entityClass"])
interface TopicSchema : Entity<TopicSchema> {
    companion object : Entity.Factory<TopicSchema>()

    val topic: String
    val name: String
    var example: String
}

object TopicsSchemas : Table<TopicSchema>("topics") {
    val topic = varchar("topic").bindTo(TopicSchema::topic)
    val name = varchar("name").bindTo(TopicSchema::name)
    val example = json<String>("example").bindTo(TopicSchema::example)
}
