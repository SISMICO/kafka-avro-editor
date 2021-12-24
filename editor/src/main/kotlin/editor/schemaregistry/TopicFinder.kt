package editor.schemaregistry

import Properties
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.jackson.responseObject

class TopicFinder() {

    fun getAllTopics(): List<String> {
        return "${Properties.schemaRegistryServer}/subjects".httpGet()
            .responseObject<List<String>>().third.get()
    }

}