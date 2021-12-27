import calculator.Compute;
import calculator.ICompute;
import editor.Editor;
import editor.EditorClassesLoader;
import editor.EditorConsole;
import editor.Properties;
import editor.SchemaBuilder;
import editor.SchemaCompiler;
import editor.jsonschema.JsonSchemaConsole;
import editor.kafka.KafkaSenderConsole;
import editor.schemaregistry.SchemaRegistry;
import editor.schemaregistry.console.SchemaRegistryConsole;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        SchemaRegistryConsole schema = new SchemaRegistryConsole();
        JsonSchemaConsole json = new JsonSchemaConsole();
        KafkaSenderConsole kafka = new KafkaSenderConsole();
        Editor editor = new Editor(
                Properties.INSTANCE.getOutputPath(),
                new SchemaRegistry(),
                new SchemaCompiler(),
                new SchemaBuilder(),
                new EditorClassesLoader()
        );

        EditorConsole editorConsole = new EditorConsole(
                args,
                schema,
                json,
                kafka,
                editor
        );

        ICompute compute = new Compute();

        System.out.println("Simple Java App");
        System.out.println(String.format("Version: %d", compute.version()));
        System.out.println(String.format("Calculator: %d", compute.sum(10, 20)));

        editorConsole.run();
    }

}
