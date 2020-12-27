package bit.local.runner;

import bit.local.runner.runtimeexception.ExceptionInRun;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author lire
 * @title: IRunner
 * @projectName LexueHelper
 * @description: 运行器的接口
 */

public interface IRunner {
    void runcode() throws IOException, ExceptionInRun;
    Path getOutputFilePath();
    int checkRunStatus();
}
