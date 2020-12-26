package bit.local.runner;

public abstract class InterpretedLanguagerRunner implements IRunner{
    /**
     * 编译后可运行的文件目录
     */
    protected String dict;

    protected String compileMessage;

    public abstract String getRunMessage();
}
