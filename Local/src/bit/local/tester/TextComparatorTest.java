package bit.local.tester;

import bit.local.tools.TextComparator;
import bit.local.tools.IgnoreMode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TextComparatorTest {

    @Test
    void compareWithEndBlank1() {
        String s1 = "abc\n", s2 = "abc";
        TextComparator comparator = new TextComparator(IgnoreMode.IGNORE_BLANK_ON_END, s1);
        assertTrue(comparator.compareWith(s2));
    }
    @Test
    void compareWithEndBlank2() {
        String s1 = "abc   \n", s2 = "abc";
        TextComparator comparator = new TextComparator(IgnoreMode.IGNORE_BLANK_ON_END, s1);
        assertTrue(comparator.compareWith(s2));
    }
    @Test
    void compareWithNoBlank1() {
        String s1 = "abc ", s2 = "abc";
        TextComparator comparator = new TextComparator(IgnoreMode.IGNORE_NO_BLANK, s1);
        assertFalse(comparator.compareWith(s2));
    }
    @Test
    void compareWithAllBlank1() {
        String s1 = "abc s", s2 = "abcs";
        TextComparator comparator = new TextComparator(IgnoreMode.IGNORE_ALL_BLANK, s1);
        assertTrue(comparator.compareWith(s2));
    }
    @Test
    void compareWithAllBlank2() {
        String s1 = "abc s \r \n sdas \n", s2 = "abcssdas";
        TextComparator comparator = new TextComparator(IgnoreMode.IGNORE_ALL_BLANK, s1);
        assertTrue(comparator.compareWith(s2));
    }
}