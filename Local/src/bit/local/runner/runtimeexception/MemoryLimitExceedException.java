package bit.local.runner.runtimeexception;

/**
 * @author lire
 * @title: MemoryLimitExceedException
 * @projectName LexueHelper
 * @description: 超出空间限制
 */

public class MemoryLimitExceedException extends ExceptionInRun{
    public MemoryLimitExceedException() {
        super("Memory Limit Exceed!");
    }
}
