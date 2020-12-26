package bit.local.tester;

import bit.local.runner.PythonRunner;
import bit.local.runner.runtimeexception.CompileErrorException;
import bit.local.runner.runtimeexception.ExceptionInRun;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hlq15
 * @title: PythonRunnerTest
 * @projectName LexueHelper
 * @description: 测试Python
 * @date 2020/12/2521:40
 */
class PythonRunnerTest {

    @Test
    void runcodetest1() throws IOException, ExceptionInRun {
        PythonRunner runner = new PythonRunner("test\\a.py", "", "own.txt");
        runner.runcode();
    }

    @Test
    void runcodetest2() {
        PythonRunner runner = new PythonRunner("test\\b.py", "", "own.txt");
        assertThrows(CompileErrorException.class, runner::runcode);
    }

}