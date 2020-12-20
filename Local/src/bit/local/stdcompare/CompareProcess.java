package bit.local.stdcompare;

import bit.local.runner.RunnerFatory;
import bit.local.runner.runtimeexception.*;
import bit.local.tools.FilesInfoAttainer;
import bit.local.tools.IgnoreMode;
import bit.local.tools.SourceFileMaker;
import static bit.local.runner.RunnerFatory.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * @author lire
 * @title: CompareProcess
 * @projectName LexueHelper
 * @description: 执行对拍主过程。
 * @date 2020/12/2020:51
 */
public class CompareProcess {
    /**
     * 对拍重复次数
     */
    private int circleTime = 100;
    private String data_maker_language;
    private Path data_maker_path;
    private Path data_maker_exe_path;
    private String own_src_language;
    private Path own_src_path;
    private Path own_src_exe_path;
    private String std_src_language;
    private Path std_src_path;
    private Path std_src_exe_path;

    private IgnoreMode ignoreMode = IgnoreMode.IGNORE_NO_BLANK;
    public void setIgnoreMode(IgnoreMode ignoreMode) {
        this.ignoreMode = ignoreMode;
    }

    public Optional<CompareResult> result = Optional.empty();

    public static Path removeSameRootEXE(Path path, String languageType) throws IOException {
        SourceFileMaker maker = new SourceFileMaker();
        int idx = path.toString().lastIndexOf('\\');
        String targetSrc = path.toString().substring(idx+1);
        if (languageType.trim().equalsIgnoreCase("C++")) {
            targetSrc = targetSrc.replace("cpp","exe");
        } else if (languageType.trim().equalsIgnoreCase("C")) {
            targetSrc = targetSrc.replace("c","exe");
        } else if (languageType.trim().equalsIgnoreCase("JAVA")) {
            targetSrc = targetSrc.replace("java", "class");
        }
        Path exePath = path.getParent().resolve(targetSrc);
        maker.removeOldFile(exePath);
        return exePath;
    }

    public CompareProcess(int circleTime, String data_maker_language, Path data_maker_path,
                          String own_src_language, Path own_src_path, String std_src_language, Path std_src_path) {
        this(data_maker_language,data_maker_path,own_src_language,own_src_path,std_src_language,std_src_path);
        this.circleTime = circleTime;
    }

    public CompareProcess(String data_maker_language, Path data_maker_path, String own_src_language,
                          Path own_src_path, String std_src_language, Path std_src_path) {
        this.data_maker_language = data_maker_language;
        this.data_maker_path = data_maker_path;
        this.own_src_language = own_src_language;
        this.own_src_path = own_src_path;
        this.std_src_language = std_src_language;
        this.std_src_path = std_src_path;
        try {
            if (judgeCompileLanguage(data_maker_language) == 1) {
                data_maker_exe_path = removeSameRootEXE(data_maker_path, data_maker_language);
            }
            if (judgeCompileLanguage(own_src_language) == 1) {
                own_src_exe_path = removeSameRootEXE(own_src_path, own_src_language);
            }
            if (judgeCompileLanguage(std_src_language) == 1) {
                std_src_exe_path = removeSameRootEXE(std_src_path, std_src_language);
            }
        } catch (IOException e) {

        }
    }

    public void runMainProcess() {
        for (int i = 1; i <= circleTime; ++i) {
            System.out.println("第" + i + "组数据");
            try {
                var dm_runner = RunnerFatory.createNewRunner(data_maker_language, data_maker_path.toString(),
                        data_maker_exe_path.toString(), "input.txt", "");
                dm_runner.runcode();
                while (dm_runner.checkRunStatus() == 0);
                Thread.sleep(500);
                String test_data = FilesInfoAttainer.readStringFromFiles(Paths.get("test","input.txt"));
                System.out.println(test_data);
                var std_runner = RunnerFatory.createNewRunner(std_src_language, std_src_path.toString(),
                        std_src_exe_path.toString(), "std.txt", test_data);
                System.out.println(std_src_language + " " + std_src_path + " " + std_src_exe_path + " " + test_data);
                std_runner.runcode();
                while (std_runner.checkRunStatus() == 0);
                Thread.sleep(100);
                var own_runner = RunnerFatory.createNewRunner(own_src_language, own_src_path.toString(),
                        own_src_exe_path.toString(), "own.txt", test_data);
                System.out.println(own_src_language + " " + own_src_path + " " + own_src_exe_path + " " + test_data);
                own_runner.runcode();
                while (own_runner.checkRunStatus() == 0);
                Thread.sleep(100);
                boolean compareResult = FilesInfoAttainer.judgeFileEquals(Paths.get("test","own.txt"),
                        Paths.get("test","std.txt"),ignoreMode);
                if (compareResult == false) {
                    throw new WrongAnswerException();
                }
                Thread.sleep(1000);
            } catch (ExceptionInRun e) {
                if (e instanceof CompileErrorException)
                    result = Optional.of(CompareResult.COMPILE_ERROR);
                else if (e instanceof TimeLimitExceedException)
                    result = Optional.of(CompareResult.TIME_LIMIT_EXCEED);
                else if (e instanceof RuntimeErrorException)
                    result = Optional.of(CompareResult.RUNTIME_ERROR);
                else if (e instanceof WrongAnswerException)
                    result = Optional.of(CompareResult.WRONG_ANSWER);
                return;
            } catch (IOException e) {
                result = Optional.of(CompareResult.UNKNOWN_ERROR);
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        result = Optional.of(CompareResult.PASS_TEST);
    }
}
