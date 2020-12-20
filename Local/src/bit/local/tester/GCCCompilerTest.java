package bit.local.tester;

import bit.local.compiler.GCCCompiler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class GCCCompilerTest {

    @Test
    @DisplayName("能正确编译没有问题的程序")
    void compileTest() throws IOException {
        GCCCompiler compiler = new GCCCompiler();
        compiler.compile("#include<iostream>\nusing namespace std;\nint main(){\ncout<<\"helloworld\"<<endl;\n}");
    }
    @Test
    @DisplayName("能正确编译")
    void compileTestCompileError() throws IOException {
        GCCCompiler compiler = new GCCCompiler();
        compiler.compile("#include<iostream>\nusing namespace std\nint main(){\ncout<<\"helloworld\"<<endl;\n}");
    }
    @Test
    @DisplayName("能处理Warnings")
    void compileTestCompileWarning() throws IOException {
        GCCCompiler compiler = new GCCCompiler();
        compiler.compile("#include<iostream>\nusing namespace std;\nint main(){\nint a;\nchar s[30];\ngets(s);\n}");
    }
}