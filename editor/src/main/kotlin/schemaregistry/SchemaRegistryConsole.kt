package schemaregistry

class SchemaRegistryConsole {

    companion object {
        private const val COMMAND_LIST = "--list"
        private const val COMMAND_GET = "--get"
        private const val COMMAND_ADD = "--add"
    }

    init {
        println("""
            Welcome to Schema Registry Utility
            
            --list  To List All Topics And Schemas
            --get   To get a specific Schema
            --add   To Add or Update a Schema
            
        """.trimIndent())
    }

    fun readCommand(args: Array<String>) {
        if (args.contains(COMMAND_LIST)) list()
    }

    private fun list() {
        println("Listing All Schemas")
        val schemas = SchemaRegistry().getAllSchemas()
        schemas.forEach {
            println("${it.topic}: ${it.schema}")
        }
    }

}