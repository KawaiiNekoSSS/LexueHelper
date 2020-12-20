package bit.local.runner;

import bit.local.runner.runtimeexception.ExceptionInRun;

import java.io.IOException;

public interface IRunner {
    void runcode() throws IOException, ExceptionInRun;
    void checkRunStatus();
    void getOutPut();
}
