package bit.local.tools;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class SourceFileMaker {

    public void createDir(Path path) throws IOException {
        if (Files.exists(path)) return;
        Files.createDirectories(path);
    }

    public void createFile(Path path) throws IOException {
        if (Files.exists(path)) return;
        Files.createFile(path);
    }

    public void writeFile(String content, Path path) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            throw e;
        }
    }

    public void removeOldFile(Path path) throws IOException {
        if (!Files.exists(path)) return;
        Files.delete(path);
    }

}
