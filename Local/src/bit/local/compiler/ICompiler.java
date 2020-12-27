package bit.local.compiler;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author lire
 * @title: 编译接口
 * @projectName LexueHelper
 * @description: 编译的接口
 */

public interface ICompiler {
    /**
     * 编译文本
     * @param content 以字符串形式储存的源文件
     * @throws IOException IO异常
     */
    void compile(String content) throws IOException;

    /**
     * 编译文本
     * @param content 以字符串形式储存的源文件
     * @param compileArgs 编译参数
     * @throws IOException IO异常
     */
    void compile(String content, String compileArgs) throws IOException;

    /**
     * 编译文件
     * @param targetFile 传入Path实现编译
     * @param compileArgs 编译参数
     * @throws IOException IO异常
     */
    void compile(Path targetFile, String compileArgs) throws IOException;
    /**
     * 编译文件
     * @param targetFile 传入Path实现编译
     * @throws IOException IO异常
     */
    void compile(Path targetFile) throws  IOException;
    String getTargetDict();
    CompileStatus checkCompileStatus();
    String getCompileMessage();
}
