package bit.local.runner.runtimeexception;

/**
 * @author lire
 * @title: RuntimeErrorException
 * @projectName LexueHelper
 * @description: 超出时间限制
 */

public class TimeLimitExceedException extends ExceptionInRun {
    public TimeLimitExceedException() {
        super("Time Limit Exceed!");
    }
}
