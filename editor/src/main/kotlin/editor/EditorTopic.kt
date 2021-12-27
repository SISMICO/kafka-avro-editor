package editor

import java.io.File

data class EditorTopic(
    val topic: String,
    val className: String,
    val classPath: File,
    val schemaPath: File
)
