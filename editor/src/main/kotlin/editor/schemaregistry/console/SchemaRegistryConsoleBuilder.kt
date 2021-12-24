package editor.schemaregistry.console

class SchemaRegistryConsoleBuilder {
    companion object {
        private const val COMMAND_LIST = "--list"
    }

    var isList = false
        private set
    var topic: String? = null
        private set

    fun parserCommand(command: String) {
        when (command) {
            COMMAND_LIST -> isList = true
            else -> setParameter(command)
        }
    }

    private fun setParameter(param: String) {
        if (!isList)
            throw InvalidCommandException("You must use $COMMAND_LIST command before the parameter.")
        topic = param
    }
}