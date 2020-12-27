package tester;

import bit.local.stdcompare.CompareResult;
import getter.ListGetter;
import org.junit.jupiter.api.Test;
import submittest.Submiter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lire
 * @title: SubmitTesttester
 * @projectName LexueHelper
 * @description: 测试提交数据类
 * @date 2020/12/2714:06
 */
public class SubmitTesttester {

    @Test
    void runProcess() {
        String cookie = "MDLPROGLANG_MOODLE=0; Hm_lvt_d21b82dc0c0e0869fbefe05bb68b276c=1608802008" +
                ",1608883598,1608985672,1609047169; MoodleSessionMOODLE=1kjivdfm7ck02nhit1um8b5ne6; " +
                "Hm_lpvt_d21b82dc0c0e0869fbefe05bb68b276c=1609047207";
        ListGetter getter = new ListGetter(cookie);
        var l1 = getter.getCourseList();
//        System.out.println(l1.get());
        var l2 = getter.getProblemListForCourse(l1.get().get(5));
//        System.out.println(l2.get());
        var problem = getter.getProblem(l2.get().get(3));
//        System.out.println(problem.get());
        Submiter submiter = new Submiter(problem.get());
        var res = submiter.commitTest("C++", "SourceCode\\code.cpp",
                "SourceCode\\a.exe", "output.txt");
//        System.out.println(res);
        assertEquals(res, CompareResult.PASS_TEST);
    }

}
