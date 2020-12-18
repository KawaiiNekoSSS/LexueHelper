package bit.local.runner.runtimeexception;

public class OutputOutofLimitException extends Exception{
    public OutputOutofLimitException() {
        super("Output out of the maximum limit!");
    }
}
