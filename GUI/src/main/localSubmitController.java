package main;

import bit.local.tools.SourceFileMaker;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

public class localSubmitController {

    //回调
    private Main main;
    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private TextArea codeText;

    @FXML
    public void goBack() throws IOException {
        main.showDefaultWindow();
    }

    @FXML
    public void submit() throws IOException {

        SourceFileMaker fileMaker = new SourceFileMaker();

        try {
            fileMaker.createDir(Path.of("SourceCode"));
            fileMaker.createFile(Path.of("SourceCode/test.txt"));
            fileMaker.writeFile(codeText.getText(), Path.of("SourceCode/test.txt"));

        } catch (AccessDeniedException e) {
            //System.out.println("哈哈哈找到错误啦");
            e.printStackTrace();
        }

    }
}
