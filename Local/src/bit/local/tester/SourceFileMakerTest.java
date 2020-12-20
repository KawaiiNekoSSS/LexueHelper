package bit.local.tester;

import bit.local.tools.SourceFileMaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class SourceFileMakerTest {

    @Test
    @DisplayName("能创建目录")
    void createDirTest() {
        SourceFileMaker maker = new SourceFileMaker();
        try {
            maker.createDir(Paths.get("test"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("在目录不存在时，正确抛出异常")
    void createFileNoPathTest() {
        assertThrows(IOException.class, () -> {
            SourceFileMaker maker = new SourceFileMaker();
            try {
                maker.createFile(Paths.get("test", "a.cpp"));
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        });
    }

    @Test
    @DisplayName("能正确创建文件")
    void createFileTest() {
        SourceFileMaker maker = new SourceFileMaker();
        try {
            maker.createFile(Paths.get("test", "a.cpp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("能正确写入文件")
    void writeFile() {
        SourceFileMaker maker = new SourceFileMaker();
        try {
            maker.writeFile("qwq", Paths.get("test","a.cpp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}