package editor

import kotlin.test.Test
import kotlin.test.assertEquals

class PropertiesTest {

    @Test
    fun `When Database Engine was default value`() {
        val defaultValue = "sqlite3"

        val myEnum = DatabaseEngine.fromString(defaultValue)

        assertEquals(DatabaseEngine.SQLite3, myEnum)
    }
}
