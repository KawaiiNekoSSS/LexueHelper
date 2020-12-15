package bit.local.tester;

import bit.local.tools.FilesInfoAttainer;
import bit.local.tools.IgnoreMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FilesInfoAttainerTest {


    @Test
    @DisplayName("能正確讀取到内容")
    void readStringFromFilesTest() throws IOException {
        Path path = Path.of(".\\Local\\src\\tmp\\test1.txt");
        var ans = FilesInfoAttainer.readStringFromFiles(path);
        System.out.println(ans);
        assertNotNull(ans);
    }

    @Test
    @DisplayName("读取不存在文件时抛出异常")
    void readStringFromFilesNotExistTest() throws IOException {
        Path path = Path.of(".\\Local\\src\\tmp\\test.txt");
        assertThrows(IOException.class, ()->FilesInfoAttainer.readStringFromFiles(path));
    }

    @Test
    @DisplayName("严格判等")
    void testFilesEqual1() throws IOException {
        Path path1 = Path.of(".\\Local\\src\\tmp\\test1.txt");
        Path path2 = Path.of(".\\Local\\src\\tmp\\test2.txt");
        var b = FilesInfoAttainer.judgeFileEquals(path1, path2, IgnoreMode.IGNORE_NO_BLANK);
        assertEquals(b, true);
    }

    @Test
    @DisplayName("最后有空格时不能等")
    void testFilesEqual2() throws IOException {
        Path path1 = Path.of(".\\Local\\src\\tmp\\test1.txt");
        Path path2 = Path.of(".\\Local\\src\\tmp\\test3.txt");
        var b = FilesInfoAttainer.judgeFileEquals(path1, path2, IgnoreMode.IGNORE_NO_BLANK);
        assertEquals(b, false);
    }

    @Test
    @DisplayName("在结尾空格模式下正确判等")
    void testFilesEqual3() throws IOException {
        Path path1 = Path.of(".\\Local\\src\\tmp\\test1.txt");
        Path path2 = Path.of(".\\Local\\src\\tmp\\test3.txt");
        var b = FilesInfoAttainer.judgeFileEquals(path1, path2, IgnoreMode.IGNORE_BLANK_ON_END);
        assertEquals(b, true);
    }

    @Test
    @DisplayName("在结尾空格模式下正确判等")
    void testFilesEqual4() throws IOException {
        Path path1 = Path.of(".\\Local\\src\\tmp\\test1.txt");
        Path path2 = Path.of(".\\Local\\src\\tmp\\test4.txt");
        var b = FilesInfoAttainer.judgeFileEquals(path1, path2, IgnoreMode.IGNORE_ALL_BLANK);
        assertEquals(b, true);
    }

}