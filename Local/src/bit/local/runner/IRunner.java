package bit.local.runner;

import bit.local.runner.runtimeexception.ExceptionInRun;

import java.io.IOException;
import java.nio.file.Path;

public interface IRunner {
    void runcode() throws IOException, ExceptionInRun;
    Path getOutputFilePath();
    int checkRunStatus();
}
