package bit.local.tester;

import bit.local.runner.GCCRunner;
import bit.local.runner.RunnerFatory;
import bit.local.runner.runtimeexception.ExceptionInRun;
import bit.local.runner.runtimeexception.RuntimeErrorException;
import bit.local.runner.runtimeexception.TimeLimitExceedException;
import bit.local.tools.SourceFileMaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class GCCRunnerTest {

    @Test
    @DisplayName("能正确跑出a+b的代码")
    void runcodeTest1() throws IOException, ExceptionInRun {
        SourceFileMaker maker = new SourceFileMaker();
        maker.createFile(Paths.get("test", "a.cpp"));
        maker.writeFile("#include<iostream>\nusing namespace std;\n " +
                "int main(){int a,b; cin >> a >> b; cout << a <<endl; cout << b << endl;}",Paths.get("test", "a.cpp"));
        var runner = RunnerFatory.createNewRunner("C++", "test\\a.cpp",
                "a.exe","a.out", "1 \n 1");
        runner.runcode();
    }

    @Test
    @DisplayName("能判断TLE")
    void runcodeTest2() throws IOException {
        SourceFileMaker maker = new SourceFileMaker();
        maker.createFile(Paths.get("test", "a.cpp"));
        maker.writeFile("#include<iostream>\nusing namespace std;\n int main(){ while(1){} }",
                Paths.get("test", "a.cpp"));
        assertThrows(TimeLimitExceedException.class, ()->{
            var runner = RunnerFatory.createNewRunner("C++", "a.cpp",
                    "a.exe", "a.out","1 1");
            runner.runcode();
        });
    }

    @Test
    @DisplayName("能判断RE")
    void runcodeTest3() throws IOException {
        SourceFileMaker maker = new SourceFileMaker();
        maker.createFile(Paths.get("test", "a.cpp"));
        maker.writeFile("#include<iostream>\nusing namespace std;\n int main(){ cout << 2 / 0 << endl; }",
                Paths.get("test", "a.cpp"));
        assertThrows(RuntimeErrorException.class, ()->{
            var runner = RunnerFatory.createNewRunner("C++", "a.cpp",
                    "a.exe", "a.out","1 1");
            runner.runcode();
        });
    }

    @Test
    @DisplayName("能正确输出多行代码")
    void runcodeTest4() throws IOException, ExceptionInRun {
        SourceFileMaker maker = new SourceFileMaker();
        maker.createFile(Paths.get("test", "a.cpp"));
        maker.writeFile("#include<iostream>\nusing namespace std;\n int main(){int a, b; cin >> a >> b; cout << a+b << endl << a*b << endl;}",
                Paths.get("test", "a.cpp"));
        var runner = RunnerFatory.createNewRunner("C++", "a.cpp",
                "a.exe", "a.out","1 1");
        runner.runcode();
    }

}