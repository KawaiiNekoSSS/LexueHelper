package bit.local.runner.runtimeexception;

/**
 * @author lire
 * @title: OutputOutofLimitException
 * @projectName LexueHelper
 * @description: 超出最大输出限制
 */

public class OutputOutofLimitException extends ExceptionInRun{
    public OutputOutofLimitException() {
        super("Output out of the maximum limit!");
    }
}
