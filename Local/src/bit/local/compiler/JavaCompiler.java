package bit.local.compiler;

import bit.local.tools.SourceFileMaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hlq15
 * @title: JavaCompiler
 * @projectName LexueHelper
 * @description: TODO
 * @date 2020/12/2523:48
 */
public class JavaCompiler implements ICompiler {

    public static final String DEFAULT_COMPILE_ARGS = "";

    private Path srcDict;
    private Path targetDict;
    private String srcFileName;
    private String targetFileName;
    private String correctLine;
    private String errLine;

    private CompileStatus status;

    public JavaCompiler() {
        this("Main.java");
    }

    /**
     *
     * @param srcFileName 源文件名
     */

    public JavaCompiler(String srcFileName) {
        this.srcFileName = srcFileName;
    }

    @Override
    public void compile(String content) throws IOException {
        compile(content, DEFAULT_COMPILE_ARGS);
    }

    @Override
    public void compile(String content, String compileArgs) throws IOException {

    }

    /**
     * 根据读入字符串，将content写到目录下并编译
     * @param srcFile 要编译的文件
     * @param compileArgs 编译参数
     * @throws IOException IO异常
     */

    @Override
    public void compile(Path srcFile, String compileArgs) throws IOException {
        SourceFileMaker maker = new SourceFileMaker();
        Path np = Paths.get(targetFileName);
        targetDict = np;
        maker.removeOldFile(np);
        System.out.println(srcFile.toAbsolutePath().toString());
        String crs[] = new String[]{"javac",srcFile.toAbsolutePath().toString(),compileArgs};
        //Process process = new ProcessBuilder(commands).start();
        Process process = Runtime.getRuntime().exec(crs);
//        System.out.println(process.info());
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                process.getInputStream(),"gbk"));
        BufferedReader errreader = new BufferedReader(new InputStreamReader(
                process.getErrorStream(), "gbk"));
        String line;
        StringBuffer correctLineBuf = new StringBuffer(), errLineBuf = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            correctLineBuf.append(line);
        }
        while ((line = errreader.readLine()) != null) {
            errLineBuf.append(line);
        }
        this.correctLine = correctLineBuf.toString();
        this.errLine = errLineBuf.toString();
        System.out.println(correctLine);
        System.out.println(errLine);
        reader.close();
        errreader.close();
        process.destroy();
        checkStatus();
    }

    @Override
    public void compile(Path srcFile) throws IOException {
        this.compile(srcFile, DEFAULT_COMPILE_ARGS);
    }

    @Override
    public CompileStatus checkCompileStatus() {
        return this.status;
    }


    void checkStatus() {
        if (Files.exists(targetDict)) {
            if (errLine.isBlank())
                status = CompileStatus.COMPILE_SUCCESS_WITHOUT_ERRORS;
            else
                status = CompileStatus.COMPILE_SUCCESS_WITH_ERRORS;
        } else {
            status = CompileStatus.COMPILE_FAILED;
        }
    }

    @Override
    public String getTargetDict() {
        return this.targetDict.toString();
    }

    @Override
    public String getCompileMessage() {
        return errLine;
    }


}
