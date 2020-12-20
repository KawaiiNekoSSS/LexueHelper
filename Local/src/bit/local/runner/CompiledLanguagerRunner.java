package bit.local.runner;

import bit.local.runner.IRunner;
import bit.local.compiler.*;

import java.io.IOException;

public abstract class CompiledLanguagerRunner implements IRunner {
    /**
     * 编译后可运行的文件目录
     */
    protected String dict;
    protected CompileStatus status;
}