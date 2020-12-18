package bit.local.runner.runtimeexception;

public class CompileErrorException extends Exception{
    String compileInfo;
    CompileErrorException(String compileInfo) {
        super("Compile Faied!");
        this.compileInfo = compileInfo;
    }
}
