package bit.local.runner;

import bit.local.runner.IRunner;
import bit.local.compiler.*;

import java.io.IOException;

/**
 * @author lire
 * @title: 编译型语言的运行器
 * @projectName LexueHelper
 * @description: 运行编译型语言
 */

public abstract class CompiledLanguageRunner implements IRunner {
    /**
     * 编译后可运行的文件目录
     */
    protected String dict;
    protected CompileStatus status;
}