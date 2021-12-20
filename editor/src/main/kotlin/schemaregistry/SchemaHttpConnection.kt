package schemaregistry

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class SchemaHttpConnection {

    fun get(url: String): String {
        val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val reader = BufferedReader(InputStreamReader(connection.inputStream))
        var inputLine: String?
        val content = reader.readText()
        reader.close()
        return content
    }

}