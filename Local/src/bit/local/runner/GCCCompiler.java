package bit.local.runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import bit.local.tools.SourceFileMaker;

public class GCCCompiler implements ICompiler {

    public static final String DEFAULT_COMPILE_ARGS = "-Wall";

    private String srcDict;
    private String targetDict;
    private String correctLine;
    private String errLine;

    private CompileStatus status;

    public GCCCompiler() {
        this("test/a.cpp", "test/a.exe");
    }

    public GCCCompiler(String srcDict, String targetDict) {
        this.srcDict = srcDict;
        this.targetDict = targetDict;
    }

    @Override
    public void compile(String content) throws IOException {
        compile(content, DEFAULT_COMPILE_ARGS);
    }

    @Override
    public void compile(String content, String compileArgs) throws IOException {
        SourceFileMaker maker = new SourceFileMaker();
        Path path = Paths.get("test");
        maker.createDir(path);;
        path = Paths.get("test", "a.cpp");
        srcDict = path.toString();
        maker.createFile(path);
        maker.writeFile(content, path);
        Path np = path.getParent().resolve("a.exe");
        targetDict = np.toString();
        maker.removeOldFile(np);
        String crs[] = new String[]{"g++",path.toAbsolutePath().toString(),"-o",
                np.toAbsolutePath().toString(),compileArgs};
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
        checkStatus();
    }

    void checkStatus() {
        if (Files.exists(Paths.get(targetDict))) {
            if (errLine.isBlank())
                status = CompileStatus.COMPILE_SUCCESS_WITHOUT_ERRORS;
            else
                status = CompileStatus.COMPILE_SUCCESS_WITH_ERRORS;
        } else {
            status = CompileStatus.COMPILE_FAILED;
        }
    }

}
