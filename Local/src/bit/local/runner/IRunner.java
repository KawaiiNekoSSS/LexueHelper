package bit.local.runner;

import java.io.IOException;

public interface IRunner {
    void runcode() throws IOException;
    void checkRunStatus();
    void getOutPut();
}
