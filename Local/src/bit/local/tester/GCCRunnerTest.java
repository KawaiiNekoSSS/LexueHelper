package bit.local.tester;

import bit.local.runner.GCCRunner;
import bit.local.runner.runtimeexception.ExceptionInRun;
import bit.local.runner.runtimeexception.TimeLimitExceedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GCCRunnerTest {

    @Test
    @DisplayName("能正确跑出a+b的代码")
    void runcodeTest1() throws IOException, ExceptionInRun {
        GCCRunner runner = new GCCRunner("#include<iostream>\nusing namespace std;\n int main(){int a, b; cin >> a >> b; cout << a+b << endl;}"
          , "1 1", "2");
        runner.compile();
        runner.runcode();
    }

    @Test
    @DisplayName("能判断TLE")
    void runcodeTest2() throws IOException {
        GCCRunner runner = new GCCRunner("#include<iostream>\nusing namespace std;\n int main(){ while(1){} }"
         ,"", "");
        assertThrows(TimeLimitExceedException.class, ()->{
            runner.compile();
            runner.runcode();
        });
    }

    @Test
    @DisplayName("能判断RE")
    void runcodeTest3() throws IOException {
        GCCRunner runner = new GCCRunner("#include<iostream>\nusing namespace std;\n int main(){ cout << 2 / 0 << endl; }"
                ,"", "");
        assertThrows(TimeLimitExceedException.class, ()->{
            runner.compile();
            runner.runcode();
        });
    }

}