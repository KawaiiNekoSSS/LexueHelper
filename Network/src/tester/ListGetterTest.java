package tester;

import connect.LexueCourseInfo;
import getter.ListGetter;
import getter.ProblemURI;
import org.junit.jupiter.api.Test;

import java.net.URI;

/**
 * @author hlq15
 * @title: ListGetterTest
 * @projectName LexueHelper
 * @description: TODO
 * @date 2020/12/2317:54
 */
class ListGetterTest {

    String cookie = "MDLPROGLANG_MOODLE=0; MoodleSessionMOODLE=mh4p25h4acn7lqoffjngov6b2j; Hm_lvt_d21b8" +
            "2dc0c0e0869fbefe05bb68b276c=1608717373,1608720797,1608729376,1608802008; Hm_lpvt_d21b82dc0c0e" +
            "0869fbefe05bb68b276c=1608803332";

    @Test
    void getCourseListTest() {
        ListGetter getter = new ListGetter(cookie);
        System.out.println(getter.getCourseList());
    }

    @Test
    void getProblemListForCourse() {
        ListGetter getter = new ListGetter(cookie);
        System.out.println(getter.getProblemListForCourse(new LexueCourseInfo(
                URI.create("http://lexue.bit.edu.cn/course/view.php?id=5781"),
                "2020-大学计算机"
        )).get());
    }

    @Test
    void getProblemListForCourse2() {
        ListGetter getter = new ListGetter(cookie);
        System.out.println(getter.getProblemListForCourse(new LexueCourseInfo(
                URI.create("http://lexue.bit.edu.cn/course/view.php?id=5784"),
                "2020-大学计算机"
        )).get());
    }


    @Test
    void parserContentTest() {
        System.out.println(ListGetter.parseContent("<p style=\"margin: 0cm 0cm 0pt;\"><span style=\"font-family: 宋体;\">输入</span></p>\n" +
                "<p></p>\n" +
                "<p style=\"margin: 0cm 0cm 0pt;\"><span style=\"font-family: 宋体;\">输入偏序集</span><span lang=\"EN-US\">&lt;A, </span><span lang=\"EN-US\" style=\"font-family: Symbol;\"><span>£</span></span><span lang=\"EN-US\">&gt;</span><span style=\"font-family: 宋体;\">，</span><span lang=\"EN-US\">A</span><span style=\"font-family: 宋体;\">中的元素数不超过</span><span lang=\"EN-US\">20</span><span style=\"font-family: 宋体;\">个，分别用单个小写的英文字母表示。</span></p>\n" +
                "<p></p>\n" +
                "<p style=\"margin: 0cm 0cm 0pt;\"><span style=\"font-family: 宋体;\">输入的第一行给出</span><span lang=\"EN-US\">A</span><span style=\"font-family: 宋体;\">中的各个元素，两个相邻的元素之间用逗号隔开。</span></p>\n" +
                "<p></p>\n" +
                "<p style=\"margin: 0cm 0cm 0pt;\"><span style=\"font-family: 宋体;\">输入的第二行给出偏序关系</span><span lang=\"EN-US\" style=\"font-family: Symbol;\"><span>£</span></span><span style=\"font-family: 宋体;\">，用有序对的形式给出(只给出哈斯图中的满足覆盖的两个元素形成的有序对)，如</span><span lang=\"EN-US\">&lt;a,b&gt;,&lt;c,a&gt;</span><span style=\"font-family: 宋体;\">等等，两个相邻的有序对之间用逗号隔开。</span></p>\n" +
                "<p></p>\n" +
                "<p style=\"margin: 0cm 0cm 0pt;\"></p>\n" +
                "<p></p>\n" +
                "<p style=\"margin: 0cm 0cm 0pt;\"><span style=\"font-family: 宋体;\">输出</span></p>\n" +
                "<p></p>\n" +
                "<p style=\"margin: 0cm 0cm 0pt;\"><span style=\"font-family: 宋体;\">输出</span><span lang=\"EN-US\">A</span><span style=\"font-family: 宋体;\">的极小元与极大元。</span></p>\n" +
                "<p></p>\n" +
                "<p style=\"margin: 0cm 0cm 0pt;\"><span style=\"font-family: 宋体;\">输出的第一行给出各个极小元，两个相邻元素之间用逗号隔开，输出的元素要求按照英文字母的自然顺序排列输出。</span></p>\n" +
                "<p></p>\n" +
                "<p style=\"margin: 0cm 0cm 0pt;\"><span style=\"font-family: 宋体;\">输出的第二行给出各个极大元，两个相邻元素之间用逗号隔开，输出的元素要求按照英文字母的自然顺序排列输出。</span></p>"));
    }

    @Test
    void parserContentTest2() {
        System.out.println(ListGetter.parseContent("<div class=\"box py-3 description\"><div class=\"box py-3 intro\"><div class=\"no-overflow\"><p>从键盘上输入整数n，计算从1到n的累加和sum，并将累加和sum输出。</p><p>其中，<span style=\"font-size: 0.9375rem;\">sum=1+2+3+4+5……+（n-1）+n</span></p></div></div><div class=\"box py-3 testcase-table\">"));
    }

    @Test
    void getProblemTest1() {
        ListGetter getter = new ListGetter(cookie);
        var o = getter.getProblem(new ProblemURI(
                URI.create("http://lexue.bit.edu.cn/mod/programming/view.php?id=217084"), "qwq"));
        System.out.println(o.get());
    }

    @Test
    void getProblemTest2() {
        ListGetter getter = new ListGetter(cookie);
        var o = getter.getProblem(new ProblemURI(
                URI.create("http://lexue.bit.edu.cn/mod/programming/view.php?id=215721"), "qwq"));
        System.out.println(o.get());
    }

    @Test
    void getProblemTest3() {
        ListGetter getter = new ListGetter(cookie);
        var o = getter.getProblem(new ProblemURI(
                URI.create("http://lexue.bit.edu.cn/mod/programming/view.php?id=204534"), "qwq"));
        System.out.println(o.get());
    }
}