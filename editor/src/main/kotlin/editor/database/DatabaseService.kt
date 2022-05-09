package editor.database

import editor.Properties
import org.flywaydb.core.Flyway
import org.ktorm.database.Database
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DatabaseService {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun getInstance(): Database =
        Database.connect(
            url = Properties.database.url,
            driver = Properties.database.driver,
            user = Properties.database.user,
            password = Properties.database.password
        )

    fun migrate() {
        Flyway
            .configure()
            .dataSource(
                Properties.database.url,
                Properties.database.user,
                Properties.database.password
            )
            .load()
            .migrate()
    }
}
