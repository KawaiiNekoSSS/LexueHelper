package bit.local.runner;

import bit.local.compiler.CompileStatus;
import bit.local.compiler.GCCCompiler;
import bit.local.runner.runtimeexception.ExceptionInRun;
import bit.local.runner.runtimeexception.TimeLimitExceedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;

/**
 * @author lire
 * @date 2020/12/19
 *
 * GCC的运行器。
 *
 */

public class GCCRunner extends CompiledLanguagerRunner{

    /**
     * 源代码、输入、输出
     */
    private String srcCode, in, out;

    /**
     * 输出目录
     */
    private String dict;
    /**
     * 运行时限
     */
    private float timeLimit = 1.0f;
    /**
     * 运行空间限制
     */
    private int memoryLimit = 128;
    /**
     * 运行最大输出限制
     */
    private int maxOutputSizeLimit = 114514;

    /**
     *
     * @param srcCode 源代码
     * @param in  标准
     * @param out 标准输出
     */
    
    public GCCRunner (String srcCode, String in, String out) {
        this.srcCode = srcCode;
        this.in = in;
        this.out = out;
    }

    /**
     *
     * @param timeLimit 时间限制
     * @param memoryLimit  空间限制
     */
    public GCCRunner (String srcCode, String in, String out, int timeLimit, int memoryLimit) {
        this.srcCode = srcCode;
        this.in = in;
        this.out = out;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
    }

    /**
     * 调用编译器进行编译。
     * @throws IOException  会抛出IO异常
     */

    @Override
    public void compile() throws IOException {
        GCCCompiler compiler = new GCCCompiler();
        compiler.compile(srcCode);
        dict = compiler.getTargetDict();
    }

    /**
     * 获取编译状态。
     * @return  编译状态。
     */

    @Override
    public CompileStatus checkCompileStatus() {
        return this.status;
    }

    /**
     * 核心的运行函数。
     * 会抛出不同的运行状态。
     * @throws IOException
     * @throws ExceptionInRun
     */

    @Override
    public void runcode() throws IOException, ExceptionInRun {
        Path path = Paths.get(dict);
        Process process = new ProcessBuilder(dict).start();
        long startTime = System.currentTimeMillis();
        OutputStream outputStream = process.getOutputStream();
        outputStream.write(in.getBytes("GBK"));
        outputStream.flush();
        outputStream.close();

        BufferedReader testout = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errout = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuffer buf = new StringBuffer();

        try {
            while (process.isAlive()) {
                if (testout.ready()) {
                    line = testout.readLine();
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
            throw e;
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
