package bit.local.tester;

import bit.local.compiler.JavaCompiler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hlq15
 * @title: JavaCompilerTest
 * @projectName LexueHelper
 * @description: TODO
 * @date 2020/12/2612:42
 */
class JavaCompilerTest {
    @Test
    public void testJavaCompile() {
        JavaCompiler compiler = new JavaCompiler("test\\Main.java");
        try {
            compiler.compile(Paths.get("test", "Main.java"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}