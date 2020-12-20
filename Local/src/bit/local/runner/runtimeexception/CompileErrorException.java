package bit.local.runner.runtimeexception;

public class CompileErrorException extends ExceptionInRun{
    String compileInfo;
    CompileErrorException(String compileInfo) {
        super("Compile Faied!");
        this.compileInfo = compileInfo;
    }
}
