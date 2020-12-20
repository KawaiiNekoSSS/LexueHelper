package bit.local.tester;

import bit.local.runner.RunnerFatory;
import bit.local.stdcompare.CompareProcess;
import bit.local.stdcompare.CompareResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static bit.local.stdcompare.CompareProcess.*;

/**
 * @author hlq15
 * @title: CompareProcessTest
 * @projectName LexueHelper
 * @description: TODO
 * @date 2020/12/2021:08
 */
class CompareProcessTest {

    @Test
    @DisplayName("读取路径测试")
    public void testPath() throws IOException {
        Path path = Paths.get("test","qwq","a.cpp");
        removeSameRootEXE(path, "c++");
    }



    @Test
    @DisplayName("anothertest")
    public void testOwn() throws Exception {
        var own_runner = RunnerFatory.createNewRunner("C++", "test\\src.cpp",
                "test\\src.exe", "own.txt", "6 2\n");
        own_runner.runcode();
    }

    @Test
    @DisplayName("正确的对拍结果")
    public void testACPai() throws Exception {
        CompareProcess process = new CompareProcess("C++", Paths.get("test","dm.cpp"),
                "C++",Paths.get("test","src.cpp"),
                "C++",Paths.get("test","std.cpp"));
        process.runMainProcess();
        assertEquals(process.result, CompareResult.PASS_TEST);
    }

}