package main;

import bit.local.stdcompare.CompareProcess;
import bit.local.stdcompare.CompareResult;
import bit.local.tools.SourceFileMaker;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class duiPaiController {

    //回调
    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }


    @FXML
    public void goBack() throws IOException {
        main.showDefaultWindow();
    }

    @FXML
    public TextArea selfCode;
    @FXML
    public TextArea dmCode;
    @FXML
    public TextArea paiCode;

    @FXML
    private ChoiceBox selfLanguageChoice;
    @FXML
    private ChoiceBox paiLanguageChoice;
    @FXML
    private ChoiceBox dmLanguageChoice;

    @FXML
    private Label progress;

    @FXML
    public void submit() throws IOException {

        int selfLanguageIndex = selfLanguageChoice.getSelectionModel().getSelectedIndex();
        int paiLanguageIndex = paiLanguageChoice.getSelectionModel().getSelectedIndex();
        int dmLanguageIndex = dmLanguageChoice.getSelectionModel().getSelectedIndex();

        try {
            MakeFiles(selfLanguageIndex, paiLanguageIndex, dmLanguageIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Runnable runTask = () -> {
            try {
                var res = RunCode(selfLanguageIndex, paiLanguageIndex, dmLanguageIndex);
                Platform.runLater(() -> {
                    try {
                        main.openResultWindow(res.orElseThrow(() -> new UnknownError()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(runTask).start();
    }

    private void MakeFiles (int selfLanguageIndex, int paiLanguageIndex, int dmLanguageIndex) throws IOException {

        SourceFileMaker fileMaker = new SourceFileMaker();

        fileMaker.createDir(Path.of("DuiPaiFolder"));

        MakeFile(selfLanguageIndex, "selfCode", selfCode.getText());
        MakeFile(paiLanguageIndex, "paiCode", paiCode.getText());
        MakeFile(dmLanguageIndex, "dmCode", dmCode.getText());


    }

    private void MakeFile (int languageIndex, String fileName, String fileText) throws IOException {

        SourceFileMaker fileMaker = new SourceFileMaker();

        if (languageIndex == 0) {
            fileMaker.createFile(Path.of("DuiPaiFolder/" + fileName + ".cpp"));
            fileMaker.writeFile(fileText, Path.of("DuiPaiFolder/" + fileName + ".cpp"));
        }
        else if (languageIndex == 1) {
            fileMaker.createFile(Path.of("DuiPaiFolder/" + fileName + ".java"));
            fileMaker.writeFile(fileText, Path.of("DuiPaiFolder/" + fileName + ".java"));
        }
        else if (languageIndex == 2) {
            fileMaker.createFile(Path.of("DuiPaiFolder/" + fileName + ".js"));
            fileMaker.writeFile(fileText, Path.of("DuiPaiFolder/" + fileName + ".js"));
        }
        else if (languageIndex == 3) {
            fileMaker.createFile(Path.of("DuiPaiFolder/" + fileName + ".py"));
            fileMaker.writeFile(fileText, Path.of("DuiPaiFolder/" + fileName + ".py"));
        }
    }

    private Optional<CompareResult> RunCode(int selfLanguageIndex, int paiLanguageIndex, int dmLanguageIndex) {
        CompareProcess process = new CompareProcess(20,
                ChoiceBoxMap(dmLanguageIndex, 0),
                Paths.get("DuiPaiFolder","dmCode" + ChoiceBoxMap(dmLanguageIndex, 1)),
                ChoiceBoxMap(selfLanguageIndex, 0),
                Paths.get("DuiPaiFolder","selfCode" + ChoiceBoxMap(selfLanguageIndex, 1)),
                ChoiceBoxMap(paiLanguageIndex, 0),
                Paths.get("DuiPaiFolder","paiCode"+ ChoiceBoxMap(paiLanguageIndex, 1)));
        process.runMainProcess(progress);
        return process.result;
    }


    private String ChoiceBoxMap(int choiceIndex, int returnMode) {
        //returnMode=1-->返回后缀名
        //returnMode=0-->返回语言名
        if (choiceIndex == 0)
            if (returnMode == 0)
               return "C++";
            else
               return ".cpp";
        if (choiceIndex == 1)
            if (returnMode == 0)
                return "Java";
            else
                return ".java";
        if (choiceIndex == 2)
            if (returnMode == 0)
                return "Node";
            else
                return ".js";
        if (choiceIndex == 3)
            if (returnMode == 0)
                return "Python";
            else
                return ".py";
        return "";
    }
}
