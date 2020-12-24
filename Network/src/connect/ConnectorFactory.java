package connect;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Optional;

/**
 * @author lire
 * @title: ConnectorFactory
 * @projectName LexueHelper
 * @description: 用来创建客户端和请求的类
 * @date 2020/12/2317:37
 */
public class ConnectorFactory {

    /**
     * 创建一个客户端
     * @return 返回客户端
     */

    public static HttpClient createClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }

    /**
     * 创建请求服务
     * @param uri 请求的URI
     * @param cookie 请求的cookie
     * @param timeout 超时
     * @return 返回请求
     */

    public static HttpRequest createGetRequest(URI uri, String cookie, Long timeout) {
        return HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .uri(uri)
                .header("Content-Type","text/html;charset=utf-8")
                .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36")
                .header("Cookie",cookie)
                .GET()
                .timeout(Duration.ofSeconds(timeout))
                .build();
    }

}
