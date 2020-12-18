package bit.local.runner;

import bit.local.compiler.CompileStatus;
import bit.local.compiler.GCCCompiler;
import bit.local.runner.runtimeexception.TimeLimitExceedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;

public class GCCRunner extends CompiledLanguagerRunner{

    private String srcCode, in, out;
    private String dict;
    private float timeLimit = 1.0f;
    private int memoryLimit = 128;
    private int maxOutputSizeLimit = 114514;

    public GCCRunner (String srcCode, String in, String out) {
        this.srcCode = srcCode;
        this.in = in;
        this.out = out;
    }

    public GCCRunner (String srcCode, String in, String out, int timeLimit, int memoryLimit) {
        this.srcCode = srcCode;
        this.in = in;
        this.out = out;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
    }

    @Override
    public void compile() throws IOException {
        GCCCompiler compiler = new GCCCompiler();
        compiler.compile(srcCode);
        dict = compiler.getTargetDict();
    }

    @Override
    public CompileStatus checkCompileStatus() {
        return this.status;
    }

    @Override
    public void runcode() throws IOException {
        Path path = Paths.get(dict);
        Process process = new ProcessBuilder(dict).start();
        float startTime = System.currentTimeMillis();
        OutputStream outputStream = process.getOutputStream();
        outputStream.write(in.getBytes("GBK"));
        outputStream.flush();
        outputStream.close();

        BufferedReader testout = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errout = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        try {
            while (process.isAlive()) {
                if (testout.ready()) {
                    line = testout.readLine();
                    System.out.println(line);
                    continue;
                }
                if (errout.ready()) {
                    line = errout.readLine();
                    System.out.println(line);
                    continue;
                }

                if ((System.currentTimeMillis() - startTime) >= timeLimit * 1000) {
                    process.destroy();
                    throw new TimeLimitExceedException();
                }
            }
        } catch (TimeLimitExceedException e) {
            e.printStackTrace();
        } finally {
            if (process.isAlive()) process.destroy();
            testout.close();
            errout.close();
        }

    }

    @Override
    public void checkRunStatus() {

    }

    @Override
    public void getOutPut() {

    }
}
