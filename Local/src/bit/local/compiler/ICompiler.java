package bit.local.compiler;

import java.io.IOException;
import java.nio.file.Path;

public interface ICompiler {
    void compile(String content) throws IOException;
    void compile(String content, String compileArgs) throws IOException;
    void compile(Path targetFile, String compileArgs) throws IOException;
    void compile(Path targetFile) throws  IOException;
    String getTargetDict();
    CompileStatus checkCompileStatus();
    String getCompileMessage();
}
