package editor.database

import editor.DatabaseEngine
import editor.Properties
import org.flywaydb.core.Flyway
import org.ktorm.database.Database
import org.ktorm.support.postgresql.PostgreSqlDialect
import org.ktorm.support.sqlite.SQLiteDialect
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DatabaseService {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun getInstance(): Database =
        Database.connect(
            url = Properties.database.url,
            driver = Properties.database.driver,
            user = Properties.database.user,
            password = Properties.database.password,
            dialect = when (Properties.database.engine) {
                DatabaseEngine.PostgreSQL -> PostgreSqlDialect()
                DatabaseEngine.SQLite3 -> SQLiteDialect()
                else -> SQLiteDialect()
            }
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
