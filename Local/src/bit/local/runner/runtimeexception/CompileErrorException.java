package bit.local.runner.runtimeexception;

public class CompileErrorException extends ExceptionInRun{
    String compileInfo;
    public CompileErrorException(String compileInfo) {
        super("Compile Failed!");
        this.compileInfo = compileInfo;
    }
}
