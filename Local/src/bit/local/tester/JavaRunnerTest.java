package bit.local.tester;

import bit.local.runner.JavaRunner;
import bit.local.runner.runtimeexception.ExceptionInRun;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lire
 * @title: JavaRunnerTest
 * @projectName LexueHelper
 * @description: 测试java代码
 * @date 2020/12/2613:48
 */
class JavaRunnerTest {
    @Test
    void testRun1() {
        JavaRunner runner = new JavaRunner("test\\Main", "", "own.txt");
        try {
            runner.runcode();
        } catch (IOException | ExceptionInRun e) {
            e.printStackTrace();
        }
    }
}