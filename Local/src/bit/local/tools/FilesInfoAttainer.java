package bit.local.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FilesInfoAttainer {
    public static String readStringFromFiles(Path path) throws IOException {
        try {
            if (!Files.exists(path)) throw new FileNotFoundException();
            byte[] bytes = Files.readAllBytes(path);
            String s = new String(bytes, StandardCharsets.UTF_8);
            return s;
        } catch (IOException e) {
            throw e;
        }
    }
    public static boolean judgeFileEquals(Path file1, Path file2, IgnoreMode mode) throws IOException {
        String s1 = readStringFromFiles(file1);
        String s2 = readStringFromFiles(file2);
        var comparator = new TextComparator(mode, s1);
        return comparator.compareWith(s2);
    }
}
