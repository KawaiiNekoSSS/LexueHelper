package connect;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lire
 * @title: LexueConnecter
 * @projectName LexueHelper
 * @description: 用来连接到乐学的连接器==
 * @date 2020/12/2216:12
 */
public class LexueConnecter {

    public static String doGet(String url) {
        try {
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .uri(URI.create("http://lexue.bit.edu.cn/my/"))
                    .header("Content-Type","text/html;charset=utf-8")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36")
                    .header("Cookie","MDLPROGLANG_MOODLE=0; MoodleSessionMOODLE=duv85lior1e" +
                            "jpt05uelsaddpsv; Hm_lvt_d21b82dc0c0e0869fbefe05bb68b276c=1608163633,1608194466" +
                            ",1608516925,1608624925; Hm_lpvt_d21b82dc0c0e0869fbefe05bb68b276c=1608627605")
                    .GET()
                    .timeout(Duration.ofSeconds(2))
                    .build();
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
            System.out.println(lst);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
