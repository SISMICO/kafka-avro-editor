import org.apache.avro.Schema
import org.apache.avro.file.DataFileWriter
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericDatumWriter
import org.apache.avro.generic.GenericRecord
import org.apache.avro.io.DatumWriter
import org.apache.avro.specific.SpecificDatumWriter
import java.io.File


class FromSchema {

    fun generateAvro(pathSchema: String) {
        val schema = Schema.Parser().parse(File(pathSchema))
        val data = GenericData.Record(schema)
        data.put("name", "Leonardo Bento")
        data.put("birthDate", "1985-02-07")

        val datum = GenericDatumWriter<GenericRecord>(schema)
        val fileWriter = DataFileWriter(datum)
        fileWriter.create(schema, File("/tmp/kafka-avro-editor/schemas/avro/person.avro"))
        fileWriter.append(data)
        fileWriter.close()
    }

}