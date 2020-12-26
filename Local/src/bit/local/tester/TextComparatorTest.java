package bit.local.tester;

import bit.local.tools.TextComparator;
import bit.local.tools.IgnoreMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lire
 * @title: RunnerFactoryTest
 * @projectName LexueHelper
 * @description: 测试对拍器
 * @date 2020/12/2614:17
 */

class TextComparatorTest {

    @Test
    @DisplayName("能忽略行尾空格")
    void compareWithEndBlank1() {
        String s1 = "abc\n", s2 = "abc";
        TextComparator comparator = new TextComparator(IgnoreMode.IGNORE_BLANK_ON_END, s1);
        assertTrue(comparator.compareWith(s2));
    }
    @Test
    @DisplayName("能忽略多个换行符")
    void compareWithEndBlank2() {
        String s1 = "abc   \n", s2 = "abc";
        TextComparator comparator = new TextComparator(IgnoreMode.IGNORE_BLANK_ON_END, s1);
        assertTrue(comparator.compareWith(s2));
    }
    @Test
    @DisplayName("能不忽略换行的比较")
    void compareWithNoBlank1() {
        String s1 = "abc ", s2 = "abc";
        TextComparator comparator = new TextComparator(IgnoreMode.IGNORE_NO_BLANK, s1);
        assertFalse(comparator.compareWith(s2));
    }
    @Test
    @DisplayName("能忽略所有空格比较")
    void compareWithAllBlank1() {
        String s1 = "abc s", s2 = "abcs";
        TextComparator comparator = new TextComparator(IgnoreMode.IGNORE_ALL_BLANK, s1);
        assertTrue(comparator.compareWith(s2));
    }
    @Test
    @DisplayName("能忽略所有空格比较")
    void compareWithAllBlank2() {
        String s1 = "abc s \r \n sdas \n", s2 = "abcssdas";
        TextComparator comparator = new TextComparator(IgnoreMode.IGNORE_ALL_BLANK, s1);
        assertTrue(comparator.compareWith(s2));
    }
}