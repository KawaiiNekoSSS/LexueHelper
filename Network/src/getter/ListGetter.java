package getter;

import connect.LexueCourseInfo;
import connect.ConnectorFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lire
 * @title: ListGetter
 * @projectName LexueHelper
 * @description: 爬虫过程，爬取各个类
 * @date 2020/12/2317:46
 */
public class ListGetter {

    private HttpClient client;
    private String cookie;
    public static final String MY_COURSE_URL = "http://lexue.bit.edu.cn/my/";


    public ListGetter(String cookie) {
        client = ConnectorFactory.createClient();
        this.cookie = cookie;
    }

    public Optional<List<LexueCourseInfo>> getCourseList() {
        try {
            var request = ConnectorFactory.createGetRequest(URI.create(MY_COURSE_URL),
                    cookie, 2L);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String req = response.body();
            Pattern pattern = Pattern.compile("<a class=\"list-group-item list-group-item-action \" " +
                            "href=\"(http://lexue\\.bit\\.edu\\.cn/course/view\\.php\\?id=\\d+)\"" +
                            " data-key=\"\\d+\".*?<span class=\"media-body \">(.*?)</span>.*?</a>"
                    , Pattern.DOTALL);
            Matcher matcher = pattern.matcher(req);
            System.out.println(matcher.matches());
            List<LexueCourseInfo> lst = new ArrayList<>();
            while (matcher.find()) {
                lst.add(new LexueCourseInfo(URI.create(matcher.group(1)), matcher.group(2)));
            }
            return Optional.of(lst);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<ProblemURI>> getProblemListForCourse(LexueCourseInfo info) {
        try {
            var request = ConnectorFactory.createGetRequest(info.getUri(),
                    cookie, 2L);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String req = response.body();
//            System.out.println(req);
            // Pattern pattern = Pattern.compile("href=\"(.*?programming/.*?)\".*?<span class=\"instancename\">(.*?)<span", Pattern.DOTALL);
            Pattern pattern = Pattern.compile("href=\"(http://lexue\\.bit\\.edu\\.cn/mod/" +
                    "programming/view\\.php\\?id=\\d+)\">.*?<span class=\"instancename\">(.*?)<span");
            Matcher matcher = pattern.matcher(req);
            System.out.println(matcher.matches());
            List<ProblemURI> lst = new ArrayList<>();
            while (matcher.find()) {
                lst.add(new ProblemURI(URI.create(matcher.group(1)), matcher.group(2)));
            }
            return Optional.of(lst);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static String parseContent(String s) {
        s = s.replaceAll("</p>","\n");
        s = s.replaceAll("<.*?>", "");
        s = s.replaceAll("&lt;","<");
        s = s.replaceAll("&gt;",">");
        s = s.replaceAll("&amp;","&");
        s = s.replaceAll("&quot;","\"");
        s = s.replaceAll("\\n+","\n");
        return s;
    }

    public static LocalDate convertDate(String s) {
        Pattern pattern = Pattern.compile("(\\d+)年(\\d+)月(\\d+)日.*(\\d+):(\\d+)");
        Matcher matcher = pattern.matcher(s);
        matcher.find();
        int year = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(2));
        int day = Integer.parseInt(matcher.group(3));
        return LocalDate.of(year, month, day);
    }

    public Optional<Problem> getProblem(ProblemURI uri) {
        try {
            var request = ConnectorFactory.createGetRequest(uri.getUri(),
                    cookie, 2L);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String req = response.body();
            Pattern pattern = Pattern.compile("<div class=\"box py-3 description\">(.*?)</div>",Pattern.DOTALL);
            Matcher matcher = pattern.matcher(req);
            matcher.find();
            var desp =  matcher.group(1);
            desp = parseContent(desp);
            System.out.println(desp);
            pattern = Pattern.compile("<td class=\"cell c3 lastcol\" style=\"\">(.*?)</td>",Pattern.DOTALL);
            matcher = pattern.matcher(req);
            matcher.find();
            matcher.find();
            var discountTime = matcher.group(1);
            var discountDate = convertDate(discountTime);
            matcher.find();
            var closeTime = matcher.group(1);
            var closeDate = convertDate(closeTime);
//            System.out.println(discountTime);
//            System.out.println(discountDate);
//            System.out.println(closeTime);
//            System.out.println(closeDate);
            Problem problem = new Problem(discountDate, closeDate, desp);
//            pattern = Pattern.compile("<tbody><tr class=\"lastrow\">.*?a href=\"(.*?)\".*?a href=\"(.*?)\".*?" +
//                    "<td class=\"cell c3\" style=\"\">(.*?)</td><td class=\"cell c4\" style=\"\">(.*?)</td>",
//                    Pattern.DOTALL);
            pattern = Pattern.compile("<td class=\"programming-io cell c1\" style=\"\">.*?a href=\"(.*?)\".*?a href=\"(.*?)\"" +
                    ".*?<td class=\"cell c3\" style=\"\">(.*?)</td>.*?<td class=\"cell c4\" style=\"\">(.*?)</td>",
                    Pattern.DOTALL);
            matcher = pattern.matcher(req);
            while (matcher.find()) {
                URI in = URI.create(matcher.group(1));
                URI out = URI.create(matcher.group(2));
                String timeLimit = matcher.group(3);
                String memoryLimit = matcher.group(4);
                System.out.println(in.toString() + " " + out.toString() + " " + timeLimit + " " + memoryLimit);
                Thread.sleep(500L);
                var inPutReq = ConnectorFactory.createGetRequest(in, cookie, 2L);
                HttpResponse<String> resin = client.send(inPutReq, HttpResponse.BodyHandlers.ofString());
                String indata = resin.body();
//                System.out.println(indata);
                Thread.sleep(500L);
                var outPutReq = ConnectorFactory.createGetRequest(out, cookie, 2L);
                HttpResponse<String> resout = client.send(outPutReq, HttpResponse.BodyHandlers.ofString());
                String outdata = resout.body();
//                System.out.println(outdata);
                TestData testData = new TestData(indata, outdata, timeLimit, memoryLimit);
                problem.addData(testData);
            }
            return Optional.of(problem);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
