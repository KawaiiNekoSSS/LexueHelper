package bit.local.runner;

import bit.local.runner.runtimeexception.ExceptionInRun;
import bit.local.runner.runtimeexception.RuntimeErrorException;
import bit.local.runner.runtimeexception.TimeLimitExceedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

/**
 * @author lire
 * @title: GCCRunner
 * @projectName LexueHelper
 * @description: 运行C++文件
 */

public class GCCRunner extends CompiledLanguageRunner {

    /**
     * 源代码、输入
     */
    private String srcCode, in;

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
     * 输出文件文件名
     */
    private String outputFileName = "a.out";
    /**
     * 输出文件的path
     */
    private Path outputFilePath;
    /**
     * 运行的返回结果
     */
    private int runExitValue;
    /**
     * 运行状态
     */
    private int status = 0;

    /**
     *
     * @param timeLimit 时间限制
     * @param memoryLimit  空间限制
     */
    public GCCRunner (String runDict, String in, int timeLimit, int memoryLimit) {
        this.dict = runDict;
        this.in = in;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
    }

    /**
     *
     * @param outputFileName 输出文件名
     */

    public  GCCRunner (String runDict, String in, String outputFileName) {
        this.dict = runDict;
        this.in = in;
        this.outputFileName = outputFileName;
    }

    /**
     * 获取编译状态。
     * @return  编译状态。
     */


    /**
     * 核心的运行函数。
     * 会抛出不同的运行状态。
     * @throws IOException
     * @throws ExceptionInRun
     */

    @Override
    public void runcode() throws IOException, ExceptionInRun {
        Path path = Paths.get(dict);
        System.out.println(dict);
        String rootPath = path.getParent().toAbsolutePath().toString();
        String crs[] = new String[]{"powershell","cd",rootPath,";",dict};
//        System.out.println(List.of(crs));
        Process process = new ProcessBuilder(dict).start();
//        Process process = Runtime.getRuntime().exec(crs);
        long startTime = System.currentTimeMillis();
        OutputStream outputStream = process.getOutputStream();
        outputStream.write(in.getBytes("GBK"));
        outputStream.flush();
        outputStream.close();

        BufferedReader testout = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errout = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        int byteEle;
        String errLine;
        StringBuffer wrongbuf = new StringBuffer();

        outputFilePath = Paths.get(dict).getParent().resolve(outputFileName);
        System.out.println(outputFilePath);
        var outBuf = Files.newBufferedWriter(outputFilePath, StandardCharsets.UTF_8);
        try {
            while (true) {
                if (testout.ready()) {
                    byteEle = testout.read();
//                    System.out.print(byteEle);
                    outBuf.write(byteEle);
//                    outBuf.flush();
                    continue;
                }
                if (errout.ready()) {
                    errLine = errout.readLine();
                    wrongbuf.append(errLine).append('\n');
                    continue;
                }
                if (process != null) {
                    try {
                        runExitValue = process.exitValue();
                        break;
                    } catch (IllegalThreadStateException e) {
                    }
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
            outBuf.flush();
            outBuf.close();
            if (process.isAlive()) process.destroy();
            testout.close();
            errout.close();
            System.out.println("Write End!");
            status = 1;
        }
        if (runExitValue > 127 || runExitValue < -128) throw new RuntimeErrorException();

    }

    @Override
    public Path getOutputFilePath() {
        return outputFilePath;
    }

    @Override
    public int checkRunStatus() {
        return status;
    }
}
