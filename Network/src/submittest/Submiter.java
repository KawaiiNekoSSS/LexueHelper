package submittest;

import bit.local.runner.RunnerFatory;
import bit.local.runner.runtimeexception.*;
import bit.local.stdcompare.*;
import bit.local.tools.FilesInfoAttainer;
import bit.local.tools.IgnoreMode;
import bit.local.tools.TextComparator;
import getter.Problem;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author lire
 * @title: Submiter
 * @projectName LexueHelper
 * @description: 用来提交测试
 * @date 2020/12/2713:37
 */
public class Submiter {

    private Problem problem;

    public Submiter (Problem problem) {
        this.problem = problem;
    }

    public CompareResult commitTest(String languageName, String srcFileName, String outputExecutableName,
                                    String outputDataName) {

        for(var data : problem.getDataList()) {
            try {
                var runner = RunnerFatory.createNewRunner(languageName, srcFileName,
                        outputExecutableName ,outputDataName, data.getInoutData());
                runner.runcode();
                TextComparator comparator = new TextComparator(IgnoreMode.IGNORE_BLANK_ON_END,
                        data.getOutputData());
//                Path path = Paths.get("SourceCode", "stdOutput.txt");
//                SourceFileMaker maker = new SourceFileMaker();
//                maker.createFile(path);
//                maker.writeFile(data.getOutputData(), path);
                if (!comparator.compareWith(FilesInfoAttainer.readStringFromFiles(
                        Paths.get("SourceCode", outputDataName)))) {
                    throw new WrongAnswerException();
                }
            } catch (ExceptionInRun | IOException e) {
                if (e instanceof TimeLimitExceedException)
                    return CompareResult.TIME_LIMIT_EXCEED;
                if (e instanceof RuntimeErrorException)
                    return CompareResult.RUNTIME_ERROR;
                if (e instanceof CompileErrorException)
                    return CompareResult.COMPILE_ERROR;
                if (e instanceof WrongAnswerException)
                    return CompareResult.WRONG_ANSWER;
                else
                    return CompareResult.UNKNOWN_ERROR;
            }
        }
        return CompareResult.PASS_TEST;
    }

}
