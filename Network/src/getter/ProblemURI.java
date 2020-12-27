package getter;

import java.net.URI;

/**
 * @author lire
 * @title: Problem
 * @projectName LexueHelper
 * @description: 用来描述乐学题目
 * @date 2020/12/2318:52
 */
public class ProblemURI {
    private URI uri;
    private String problemName;

    public ProblemURI(URI uri, String problemName) {
        this.uri = uri;
        this.problemName = problemName;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "uri=" + uri +
                ", problemName='" + problemName + '\'' +
                '}';
    }
}
