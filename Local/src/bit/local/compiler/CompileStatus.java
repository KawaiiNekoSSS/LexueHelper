package bit.local.compiler;

/**
 * @author lire
 * @title: CompileStatus
 * @projectName LexueHelper
 * @description: 储存编译状态
 */

public enum CompileStatus {
    /**
     * 文件IO异常的情况
     */
    CANNOT_CREATE_SOURCE_FILE,
    /**
     * 编译失败
     */
    COMPILE_FAILED,
    /**
     * 编译成功且无警告
     */
    COMPILE_SUCCESS_WITHOUT_ERRORS,
    /**
     * 编译成功但有警告
     */
    COMPILE_SUCCESS_WITH_ERRORS
}
