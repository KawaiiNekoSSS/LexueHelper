package bit.local.tester;

import bit.local.runner.GCCCompiler;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GCCCompilerTest {

    @Test
    void compileTest() throws IOException {
        GCCCompiler compiler = new GCCCompiler();
        compiler.compile("#include<iostream>\nusing namespace std;\nint main(){\ncout<<\"helloworld\"<<endl;\n}");
    }
    @Test
    void compileTestCompileError() throws IOException {
        GCCCompiler compiler = new GCCCompiler();
        compiler.compile("#include<iostream>\nusing namespace std\nint main(){\ncout<<\"helloworld\"<<endl;\n}");
    }
    @Test
    void compileTestCompileWarning() throws IOException {
        GCCCompiler compiler = new GCCCompiler();
        compiler.compile("#include<iostream>\nusing namespace std;\nint main(){\nint a;\nchar s[30];\ngets(s);\n}");
    }
}