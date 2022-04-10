package editor.entity

import org.ktorm.schema.varchar
import org.ktorm.entity.Entity
import org.ktorm.schema.Table

interface TopicSchema : Entity<TopicSchema> {
    companion object : Entity.Factory<TopicSchema>()

    val topic: String
    val name: String
    var example: String
}

object TopicsSchemas : Table<TopicSchema>("topics") {
    val topic = varchar("topic").bindTo(TopicSchema::topic)
    val name = varchar("name").bindTo(TopicSchema::name)
    val example = varchar("example").bindTo(TopicSchema::example)
}
