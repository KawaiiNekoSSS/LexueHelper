package getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lire
 * @title: Problem
 * @projectName LexueHelper
 * @description: 描述整个问题。
 * @date 2020/12/2417:28
 */
public class Problem {

    /**
     * 折扣时间
     */

    private LocalDate discountTime;

    /**
     * 关闭时间
     */

    private LocalDate closeTime;

    /**
     * 题面
     */

    private String description;

    /**
     * 样例数据集
     */

    private List<TestData> data;

    /**
     * 构造器.
     * testdata需要后续加入。
     * @param discountTime 折扣时间
     * @param closeTime 关闭时间
     * @param description 描述
     */

    public Problem(LocalDate discountTime, LocalDate closeTime, String description) {
        this.discountTime = discountTime;
        this.closeTime = closeTime;
        this.description = description;
        data = new ArrayList<>();
    }

    public LocalDate getDiscountTime() {
        return discountTime;
    }

    public void setDiscountTime(LocalDate discountTime) {
        this.discountTime = discountTime;
    }

    public LocalDate getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalDate closeTime) {
        this.closeTime = closeTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TestData> getDataList() {
        return data;
    }

    public void addData(TestData testData) {
        data.add(testData);
    }

    @Override
    public String toString() {
        return "Problem{" +
                "discountTime=" + discountTime +
                ", closeTime=" + closeTime +
                ", description='" + description + '\'' +
                ", data=" + data +
                '}';
    }
}
