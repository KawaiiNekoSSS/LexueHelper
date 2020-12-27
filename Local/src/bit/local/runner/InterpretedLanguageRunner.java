package bit.local.runner;

/**
 * @author lire
 * @title: InterpretedLanguageRunner
 * @projectName LexueHelper
 * @description: 解释型语言的运行器
 */

public abstract class InterpretedLanguageRunner implements IRunner{
    /**
     * 编译后可运行的文件目录
     */
    protected String dict;

    protected String compileMessage;

    public abstract String getRunMessage();
}
