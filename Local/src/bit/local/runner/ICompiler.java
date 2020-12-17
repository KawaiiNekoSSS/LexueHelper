package bit.local.runner;

import java.io.IOException;

public interface ICompiler {
    void compile(String content) throws IOException;
    void compile(String content, String compileArgs) throws IOException;
}
