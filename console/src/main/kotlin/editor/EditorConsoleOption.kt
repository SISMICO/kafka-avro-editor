package editor

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.Option

interface EditorConsoleOption {
    val options: List<Option>

    fun run(commandLine: CommandLine)
}
