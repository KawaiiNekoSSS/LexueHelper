package main;

import bit.local.stdcompare.CompareProcess;
import bit.local.stdcompare.CompareResult;
import bit.local.tools.FilesInfoAttainer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;

public class resultWindowController {

    private Stage stage;

    public void init(Main main,Stage stage) {
        this.stage=stage;
    }

    @FXML
    public void close() {
        stage.close();
    }

    @FXML
    Label resultLabel;
    @FXML
    Label wrongTest;


    public void setMessage(CompareResult result) throws IOException {
        resultLabel.setText(ResultMap(result));

        if (result == CompareResult.WRONG_ANSWER) {

            String wrongTestStr = FilesInfoAttainer.readStringFromFiles(Path.of("DuiPaiFolder", "input.txt"));

            wrongTest.setText(wrongTestStr);
        }
    }

    private String ResultMap(CompareResult result) {
        switch (result) {
            case PASS_TEST -> {
                return "AC:测试通过";
            }
            case COMPILE_ERROR -> {
                return "CE:编译失败";
            }
            case TIME_LIMIT_EXCEED -> {
                return "TLE:运行超时";
            }
            case RUNTIME_ERROR -> {
                return "RE:运行时错误";
            }
            case UNKNOWN_ERROR -> {
                return "UKE:提交失败，请稍后重试";
            }
            case WRONG_ANSWER -> {
                return "WA:错误的结果";
            }
            default -> {
                return "请重试";
            }
        }
    }
}
