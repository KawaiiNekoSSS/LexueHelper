package bit.local.runner.runtimeexception;

public class TimeLimitExceedException extends Exception {
    public TimeLimitExceedException() {
        super("Time Limit Exceed!");
    }
}
