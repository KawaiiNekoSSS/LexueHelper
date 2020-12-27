package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import bit.local.tools.FilesInfoAttainer;

import java.io.IOException;
import java.nio.file.Path;

public class OutputModel {
    private StringProperty output = new SimpleStringProperty();

    public String getOutput() {
        return output.get();
    }

    public StringProperty outputProperty() {
        return output;
    }

    public void setOutput() throws IOException {

        String outputStr = FilesInfoAttainer.readStringFromFiles(Path.of("SourceCode", "output.txt"));

        this.output.set(outputStr);
    }
}
