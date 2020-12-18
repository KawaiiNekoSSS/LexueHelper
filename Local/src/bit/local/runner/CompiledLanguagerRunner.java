package bit.local.runner;

import bit.local.runner.IRunner;
import bit.local.compiler.*;

import java.io.IOException;

public abstract class CompiledLanguagerRunner implements IRunner {
    protected CompileStatus status;
    public abstract void compile() throws IOException;
    public abstract CompileStatus checkCompileStatus();
}