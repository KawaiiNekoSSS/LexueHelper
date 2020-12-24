package getter;

/**
 * @author lire
 * @title: TestData
 * @projectName LexueHelper
 * @description: 描述数据
 * @date 2020/12/2417:31
 */
public class TestData {

    /**
     * 输入数据
     */

    private String inoutData;

    /**
     * 输出数据
     */

    private String outputData;

    /**
     * 时间限制
     */

    private String timeLimit;

    /**
     * 空间限制
     */

    private String memoryLimit;

    public TestData(String inoutData, String outputData, String timeLimit, String memoryLimit) {
        this.inoutData = inoutData;
        this.outputData = outputData;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
    }

    public String getInoutData() {
        return inoutData;
    }

    public void setInoutData(String inoutData) {
        this.inoutData = inoutData;
    }

    public String getOutputData() {
        return outputData;
    }

    public void setOutputData(String outputData) {
        this.outputData = outputData;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(String memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    @Override
    public String toString() {
        return "TestData{" +
                "inoutData='" + inoutData + '\'' +
                ", outputData='" + outputData + '\'' +
                ", timeLimit='" + timeLimit + '\'' +
                ", memoryLimit='" + memoryLimit + '\'' +
                '}';
    }
}
