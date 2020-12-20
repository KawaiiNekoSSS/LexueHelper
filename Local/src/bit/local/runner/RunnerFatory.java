package bit.local.runner;

import bit.local.compiler.CompileStatus;
import bit.local.compiler.GCCCompiler;
import bit.local.compiler.ICompiler;
import bit.local.runner.runtimeexception.CompileErrorException;
import bit.local.tools.FilesInfoAttainer;
import bit.local.tools.SourceFileMaker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author lire
 * @title: RunnerFatory
 * @projectName LexueHelper
 * @description: 用来产生编译好的Runner。
 * @date 2020/12/2015:20
 */
public class RunnerFatory {
    private static final List<String> COMPILED_LANGUAGE = List.of("JAVA","C","C++");
    private static final List<String> INTERPRETED_LANGUAGE = List.of("PYTHON", "LUA");


    /**
     * 用来执行编译过程
     * @param compiler 编译的comiler
     * @param codePath 源代码的路径
     * @param outputExcecutableName 输出的文件名
     * @return 返回编译后文件的Path
     * @throws IOException IOException
     * @throws CompileErrorException  编译错误
     */
    public static Path compile(ICompiler compiler, Path codePath, String outputExcecutableName)
            throws IOException, CompileErrorException {
        compiler.compile(codePath);
        if (compiler.checkCompileStatus() == CompileStatus.COMPILE_FAILED)
            throw new CompileErrorException(compiler.getCompileMessage());
        String dict = compiler.getTargetDict();
        Path outputFilePath = Paths.get(outputExcecutableName);
        if (!Files.exists(outputFilePath)) {
            var maker = new SourceFileMaker();
            maker.createFile(outputFilePath);
        }
        return outputFilePath;
    }

    /**
     * 用来创建编译型语言的运行器
     * @param languageName 编程语言的名称
     * @param srcCodeFileName 源代码的文件名
     * @param outputExcecutableName 输出代码的文件名
     * @param inputMessage 输入的字符串
     * @return 编译后的运行器
     * @throws CompileErrorException 编译错误
     */

    private static Optional<CompiledLanguagerRunner> createCompiledLanguage
            (String languageName, String srcCodeFileName, String outputDataName
                    ,String outputExcecutableName,String inputMessage)
            throws CompileErrorException {
        try {
            Path srcDict = Paths.get(srcCodeFileName);
            if (languageName.equals("C++")) {
                if (Files.exists(Paths.get(outputExcecutableName))) {
                    return Optional.of(new GCCRunner(outputExcecutableName,
                            inputMessage,outputDataName));
                }
                Path targetDict = compile(new GCCCompiler(srcCodeFileName, outputExcecutableName), srcDict, outputExcecutableName);
                return Optional.of(new GCCRunner(targetDict.toString(), inputMessage,outputDataName));
            }
            return Optional.empty();

        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        } catch (CompileErrorException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static Optional<InterpretedLanguagerRunner> createInterpretedLanguageRunner
            (String languageName, String srcCodeFileName, String outputExcecutableName,String inputMessage) {
        return Optional.empty();
    }

    public static IRunner createNewRunner(String languageName, String srcFileName, String outputExcecutableName,
                                          String outputDataName, String inputMessage)
        throws CompileErrorException {
        Optional<? extends  IRunner> runner;
        if (COMPILED_LANGUAGE.contains(languageName.trim().toUpperCase())) {
            runner = createCompiledLanguage(languageName.trim().toUpperCase(),
                    srcFileName, outputDataName,outputExcecutableName, inputMessage);
        } else if (INTERPRETED_LANGUAGE.contains(languageName.trim().toUpperCase())) {
            runner = createInterpretedLanguageRunner(languageName.trim().toUpperCase(),
                    srcFileName, outputExcecutableName, inputMessage);
        } else {
            runner = Optional.empty();
        }
        IRunner myrun = runner.get();
        return myrun;
    }

    public static int judgeCompileLanguage(String languageName) {
        if (COMPILED_LANGUAGE.contains(languageName.trim().toUpperCase())) {
            return 1;
        } else if (INTERPRETED_LANGUAGE.contains(languageName.trim().toUpperCase())) {
            return 0;
        } else {
            return -1;
        }
    }

}
