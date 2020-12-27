package main;

import bit.local.tools.SourceFileMaker;
import bit.local.runner.RunnerFatory;
import bit.local.tools.FilesInfoAttainer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import models.OutputModel;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;
/**
 * @author neko
 * @title: localSubmitController
 * @projectName LexueHelper
 * @description: 本地提交的控制器
 */
public class localSubmitController implements Initializable {

    //回调
    private Main main;


    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private TextArea codeText;
    @FXML
    private TextArea inputText;
    @FXML
    private TextArea stdOutputText;
    @FXML
    private TextArea outputText;
    private OutputModel outputModel = new OutputModel();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //双向绑定
        outputText.textProperty().bindBidirectional(outputModel.outputProperty());
    }
    @FXML
    private ChoiceBox languageChoice;

    @FXML
    public void goBack() throws IOException {
        main.showDefaultWindow();
    }

    /**
     * 提交操作
     */

    @FXML
    public void submit() throws IOException {

        int languageIndex = languageChoice.getSelectionModel().getSelectedIndex();

        try {
            MakeFile(languageIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Runnable runTask = () -> {
            try {
                RunCode(languageIndex);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        new Thread(runTask).start();

    }


    private void MakeFile (int languageIndex) throws IOException {

        SourceFileMaker fileMaker = new SourceFileMaker();

        fileMaker.createDir(Path.of("SourceCode"));
        fileMaker.createFile(Path.of("SourceCode","input.txt"));
        fileMaker.createFile(Path.of("SourceCode","stdOutput.txt"));

        fileMaker.writeFile(inputText.getText(), Path.of("SourceCode/input.txt"));
        fileMaker.writeFile(stdOutputText.getText(), Path.of("SourceCode/stdOutput.txt"));

        if (languageIndex == 0) {
            fileMaker.createFile(Path.of("SourceCode", "code.cpp"));
            fileMaker.writeFile(codeText.getText(), Path.of("SourceCode/code.cpp"));
            fileMaker.removeOldFile(Path.of("SourceCode", "code.exe"));
        }
        else if (languageIndex == 1) {
            fileMaker.createFile(Path.of("SourceCode", "code.java"));
            fileMaker.writeFile(codeText.getText(), Path.of("SourceCode/code.java"));
            fileMaker.removeOldFile(Path.of("SourceCode", "code.class"));
        }
        else if (languageIndex == 2) {
            fileMaker.createFile(Path.of("SourceCode", "code.js"));
            fileMaker.writeFile(codeText.getText(), Path.of("SourceCode/code.js"));
            fileMaker.removeOldFile(Path.of("SourceCode", "code.exe"));
        }
        else if (languageIndex == 3) {
            fileMaker.createFile(Path.of("SourceCode", "code.py"));
            fileMaker.writeFile(codeText.getText(), Path.of("SourceCode/code.py"));
            fileMaker.removeOldFile(Path.of("SourceCode", "code.exe"));
        }
    }


    private void RunCode(int languageIndex) throws IOException {

        String inputMessage = FilesInfoAttainer.readStringFromFiles(Path.of("SourceCode", "input.txt"));

        if (languageIndex == 0) {
            try {
                var runner = RunnerFatory.createNewRunner("C++", "SourceCode\\code.cpp",
                        "SourceCode\\code.exe", "output.txt",inputMessage);
                runner.runcode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (languageIndex == 1){
            try {
                var runner = RunnerFatory.createNewRunner("Java", "SourceCode\\code.java",
                        "code.class", "output.txt",inputMessage);
                runner.runcode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (languageIndex == 2){
            try {
                var runner = RunnerFatory.createNewRunner("Node", "SourceCode\\code.js",
                        "", "output.txt",inputMessage);
                runner.runcode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (languageIndex == 3){
            try {
                var runner = RunnerFatory.createNewRunner("Python", "SourceCode\\code.py",
                        "", "output.txt",inputMessage);
                runner.runcode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        outputModel.setOutput();

    }
}
