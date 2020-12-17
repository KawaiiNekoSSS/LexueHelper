package bit.local.tester;

import bit.local.tools.SourceFileMaker;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class SourceFileMakerTest {

    @Test
    void createDirTest() {
        SourceFileMaker maker = new SourceFileMaker();
        try {
            maker.createDir(Paths.get("test"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
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
    void createFileTest() {
        SourceFileMaker maker = new SourceFileMaker();
        try {
            maker.createFile(Paths.get("test", "a.cpp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void writeFile() {
        SourceFileMaker maker = new SourceFileMaker();
        try {
            maker.writeFile("qwq", Paths.get("test","a.cpp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}