package editor.database

import editor.Properties
import org.flywaydb.core.Flyway
import org.ktorm.database.Database
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DatabaseService {
    companion object{
        private const val NOT_CONFIGURED_MESSAGE = "Database not configured"
    }
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun isEnabled() = !Properties.database.url.isNullOrEmpty()
    fun getInstance(): Database =
        when(isEnabled()) {
            true -> Database.connect(
                url = Properties.database.url!!,
                driver = Properties.database.driver,
                user = Properties.database.user,
                password = Properties.database.password
            )
            false -> throw DatabaseException(NOT_CONFIGURED_MESSAGE)
        }

    fun migrate() {
        when(isEnabled()) {
            true -> Flyway
                .configure()
                .dataSource(
                    Properties.database.url,
                    Properties.database.user,
                    Properties.database.password
                )
                .load()
                .migrate()
            false -> logger.info(NOT_CONFIGURED_MESSAGE)
        }
    }
}
