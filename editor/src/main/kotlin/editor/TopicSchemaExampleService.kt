package editor

import editor.entity.TopicSchema
import editor.entity.TopicsSchemas
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.configuration.ClassicConfiguration
import org.flywaydb.core.api.configuration.Configuration
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toSet

class TopicSchemaExampleService {

    private val database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/kafka",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "changeit"
    )

    fun getExamples(topic: String): Set<TopicSchema> =
        database.sequenceOf(TopicsSchemas).toSet()
}
