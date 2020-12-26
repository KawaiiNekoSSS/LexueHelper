package bit.local.runner;

import bit.local.runner.runtimeexception.CompileErrorException;
import bit.local.runner.runtimeexception.ExceptionInRun;
import bit.local.runner.runtimeexception.RuntimeErrorException;
import bit.local.runner.runtimeexception.TimeLimitExceedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author hlq15
 * @title: PythonRunner
 * @projectName LexueHelper
 * @description: 运行Python程序
 * @date 2020/12/2521:17
 */
public class PythonRunner extends InterpretedLanguagerRunner{

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

    public PythonRunner(String runDict, String in, String outputFileName) {
        this.dict = runDict;
        this.in = in;
        this.outputFileName = outputFileName;
    }

    @Override
    public void runcode() throws IOException, ExceptionInRun, CompileErrorException {
        Path path = Paths.get(dict);
        String crs[] = new String[]{"python",path.toAbsolutePath().toString()};
        //Process process = new ProcessBuilder(commands).start();
        Process process = Runtime.getRuntime().exec(crs);
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
            System.out.println(wrongbuf.toString());
            compileMessage = wrongbuf.toString();
            if (!compileMessage.isBlank()) throw new CompileErrorException(compileMessage);
//            System.out.println("Write End!");
            status = 1;
        }
        if (runExitValue > 127 || runExitValue < -128) throw new RuntimeErrorException();
    }

    @Override
    public Path getOutputFilePath() {
        return null;
    }

    @Override
    public int checkRunStatus() {
        return 0;
    }


    @Override
    public String getRunMessage() {
        return compileMessage;
    }
}
