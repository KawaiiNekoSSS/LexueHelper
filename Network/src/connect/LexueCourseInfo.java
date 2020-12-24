package connect;

import java.net.URI;
import java.net.URL;

/**
 * @author lire
 * @title: LexueCourseInfo
 * @projectName LexueHelper
 * @description: 储存从首页得到的所有课程信息
 * @date 2020/12/2217:27
 */
public class LexueCourseInfo {
    URI uri;
    String name;

    public LexueCourseInfo(URI uri, String name) {
        this.uri = uri;
        this.name = name;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LexueCourseInfo{" +
                "url=" + uri +
                ", name='" + name + '\'' +
                '}';
    }
}
