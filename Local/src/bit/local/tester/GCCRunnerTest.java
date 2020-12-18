package bit.local.tester;

import bit.local.runner.GCCRunner;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GCCRunnerTest {

    @Test
    void runcodeTest1() throws IOException {
        GCCRunner runner = new GCCRunner("#include<iostream>\nusing namespace std;\n int main(){int a, b; cin >> a >> b; cout << a+b << endl;}"
          , "1 1", "2");
        runner.compile();
        runner.runcode();
    }
}