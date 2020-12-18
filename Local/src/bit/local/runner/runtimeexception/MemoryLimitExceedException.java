package bit.local.runner.runtimeexception;

public class MemoryLimitExceedException extends Exception{
    public MemoryLimitExceedException() {
        super("Memory Limit Exceed!");
    }
}
