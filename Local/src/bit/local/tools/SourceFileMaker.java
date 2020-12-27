package bit.local.tools;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

/**
 * @author lire
 * @title: SourceFileMaker
 * @projectName LexueHelper
 * @description: 创建文件的工具类
 */

public class SourceFileMaker {

    /**
     * 创建新的目录
     * @param path 待创建目录
     */

    public void createDir(Path path) throws IOException {
        if (Files.exists(path)) return;
        Files.createDirectories(path);
    }

    /**
     * 创建新的文件
     * @param path 待创建文件
     */

    public void createFile(Path path) throws IOException {
        if (Files.exists(path)) return;
        Files.createFile(path);
    }

    /**
     * 写到文件中
     * @param content 写入内容
     * @param path 文件目录
     */

    public void writeFile(String content, Path path) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 删除文件
     * @param path 待删除内容
     */

    public void removeOldFile(Path path) throws IOException {
        if (!Files.exists(path)) return;
        Files.delete(path);
    }

}
