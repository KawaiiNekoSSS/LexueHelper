package bit.local.tester;

import bit.local.runner.RunnerFatory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lire
 * @title: RunnerFactoryTest
 * @projectName LexueHelper
 * @description: 测试Runner Factory
 * @date 2020/12/2614:17
 */

public class RunnerFactoryTest {
    @Test
    public void javaRunnerTest() {
        try {
            var runner = RunnerFatory.createNewRunner("Java", "test\\Main.java",
                    "test\\Main.class", "own.txt","");
            runner.runcode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void PythonRunnerTest() {
        try {
            var runner = RunnerFatory.createNewRunner("Python", "test\\a.py",
                    "", "own.txt","");
            runner.runcode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void NodeRunnerTest() {
        try {
            var runner = RunnerFatory.createNewRunner("Node", "test\\a.js",
                    "", "own.txt","");
            runner.runcode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void GCCRunnerTest() {
        try {
            var runner = RunnerFatory.createNewRunner("C++", "test\\src.cpp",
                    "test\\src.exe", "own.txt","1 \n 1");
            runner.runcode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
