package bit.local.stdcompare;

/**
 * @author lire
 * @title: CompareResult
 * @projectName LexueHelper
 * @description: 用来描述对拍结果。
 * @date 2020/12/2014:40
 */
public enum CompareResult {
    COMPILE_ERROR, // 编译无法通过
    RUNTIME_ERROR, // 运行时有问题
    TIME_LIMIT_EXCEED, //超时
    WRONG_ANSWER, // 结果错误
    PASS_TEST, // 通过测试
    UNKNOWN_ERROR // 发生未知错误
}
