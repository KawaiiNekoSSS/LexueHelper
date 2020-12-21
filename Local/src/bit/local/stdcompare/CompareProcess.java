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

    /**
     * 数据生成器的语言名称、所在路径和生成的exe路径。
     */

    private String data_maker_language;
    private Path data_maker_path;
    private Path data_maker_exe_path;

    /**
     * 待测试文件的语言名称、所在路径和生成的exe路径。
     */

    private String own_src_language;
    private Path own_src_path;
    private Path own_src_exe_path;

    /**
     * 标准文件的语言名称、所在路径和生成的exe路径
     */

    private String std_src_language;
    private Path std_src_path;
    private Path std_src_exe_path;

    /**
     * 文本比对忽略模式
     */

    private IgnoreMode ignoreMode = IgnoreMode.IGNORE_NO_BLANK;

    /**
     * setter.
     * 该字段应该在调用主过程前设置。
     * @param ignoreMode 忽略模式
     */

    public void setIgnoreMode(IgnoreMode ignoreMode) {
        this.ignoreMode = ignoreMode;
    }

    /**
     * 结果字段。
     * 类型是Optional，防止null pointer.
     */

    public Optional<CompareResult> result = Optional.empty();

    /**
     * 删除掉当前目录下的同名exe.
     * 防止多次重复编译的情况。
     * 因此，在第一次运行时所花时间较长。
     * @param path 当前文件所处的目录
     * @param languageType 语言类型
     * @return 返回exe文件的目录
     * @throws IOException IO异常
     */

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

    /**
     * 构造器，同时对编译型语言删除掉相同目录下的exe文件
     * @param circleTime 对拍次数
     * @param data_maker_language 数据生成器语言
     * @param data_maker_path 数据生成器目录
     * @param own_src_language 待测试语言
     * @param own_src_path 待测试文件目录
     * @param std_src_language 标程语言
     * @param std_src_path 标程目录
     */

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

    /**
     * 执行主过程。
     * 直接调用即可，会执行对拍过程。
     */

    public void runMainProcess() {
        for (int i = 1; i <= circleTime; ++i) {
            System.out.println("第" + i + "组数据");
            try {
                var dm_runner = RunnerFatory.createNewRunner(data_maker_language, data_maker_path.toString(),
                        data_maker_exe_path.toString(), "input.txt", "");
                dm_runner.runcode();
//                while (dm_runner.checkRunStatus() == 0);
//                Thread.sleep(500);
                String test_data = FilesInfoAttainer.readStringFromFiles(Paths.get("test","input.txt"));
//                System.out.println(test_data);
                var std_runner = RunnerFatory.createNewRunner(std_src_language, std_src_path.toString(),
                        std_src_exe_path.toString(), "std.txt", test_data);
//                System.out.println(std_src_language + " " + std_src_path + " " + std_src_exe_path + " " + test_data);
                std_runner.runcode();
//                while (std_runner.checkRunStatus() == 0);
//                Thread.sleep(100);
                var own_runner = RunnerFatory.createNewRunner(own_src_language, own_src_path.toString(),
                        own_src_exe_path.toString(), "own.txt", test_data);
//                System.out.println(own_src_language + " " + own_src_path + " " + own_src_exe_path + " " + test_data);
                own_runner.runcode();
//                while (own_runner.checkRunStatus() == 0);
//                Thread.sleep(100);
                boolean compareResult = FilesInfoAttainer.judgeFileEquals(Paths.get("test","own.txt"),
                        Paths.get("test","std.txt"),ignoreMode);
                if (compareResult == false) {
                    throw new WrongAnswerException();
                }
                Thread.sleep(50);
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
