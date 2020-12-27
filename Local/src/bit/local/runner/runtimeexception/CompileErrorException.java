package bit.local.runner.runtimeexception;

/**
 * @author lire
 * @title: CompileErrorException
 * @projectName LexueHelper
 * @description: 编译错误
 */

public class CompileErrorException extends ExceptionInRun{
    String compileInfo;
    public CompileErrorException(String compileInfo) {
        super("Compile Failed!");
        this.compileInfo = compileInfo;
    }
}
