package main;

import bit.local.runner.RunnerFatory;
import bit.local.stdcompare.CompareResult;
import bit.local.tools.FilesInfoAttainer;
import bit.local.tools.SourceFileMaker;
import connect.LexueCourseInfo;
import connect.error.ConnectionError;
import getter.ListGetter;
import getter.ProblemURI;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import submittest.Submiter;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class lexueSubmitController{

    //回调
    private Main main;
    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    public void goBack() throws IOException {
        main.showDefaultWindow();
    }

    private String cookie;

    @FXML
    private TextArea cookieText;
    @FXML
    private ChoiceBox courseListChoice;
    @FXML
    private ChoiceBox problemListChoice;
    @FXML
    private TextArea codeText;
    @FXML
    private ChoiceBox languageChoice;


    private Optional<Submiter> submiter = Optional.empty();

//    private IntegerProperty courseChoice = new SimpleIntegerProperty();
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        courseChoice.bind(courseListChoice.getSelectionModel().selectedIndexProperty());
//    }


    @FXML
    public void getCookie() {

        cookie = cookieText.getText();

        ListGetter getter = new ListGetter(cookie);
        List<LexueCourseInfo> courseList = null;
        try {
            courseList = getter.getCourseList().orElseThrow(ConnectionError::new);
        } catch (ConnectionError connectionError) {
            Platform.runLater(() -> {
                try {
                    main.openResultWindow("Network Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        var courseNameList = courseList.stream().map(LexueCourseInfo::getName).collect(Collectors.toList());
        courseListChoice.setItems(FXCollections.observableList(courseNameList));

        List<LexueCourseInfo> finalCourseList = courseList;
        courseListChoice.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {
                    List<ProblemURI> problemList = null;
                    try {
                        problemList = getter.getProblemListForCourse(finalCourseList.get((int)newValue)).orElseThrow(ConnectionError::new);
                    } catch (ConnectionError connectionError) {
                        Platform.runLater(() -> {
                            try {
                                main.openResultWindow("Network Error");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    var problemNameList = problemList.stream().map(ProblemURI::getProblemName).collect(Collectors.toList());
                    problemListChoice.setItems(FXCollections.observableList(problemNameList));

                    List<ProblemURI> finalProblemList = problemList;
                    problemListChoice.getSelectionModel().selectedIndexProperty().addListener(
                            (ObservableValue<? extends Number> ov2, Number oldValue2, Number newValue2) -> {
                                var problem = getter.getProblem(finalProblemList.get((int)newValue2)).orElseThrow(UnknownError::new);
                                submiter = Optional.of(new Submiter(problem));
                            }
                    );
                }
        );
    } //getCookie


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
                var res = RunCode(languageIndex);
                Platform.runLater(() -> {
                    try {
                        main.openResultWindow(res);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        new Thread(runTask).start();

    }


    private void MakeFile (int languageIndex) throws IOException {

        SourceFileMaker fileMaker = new SourceFileMaker();

        fileMaker.createDir(Path.of("LexueFolder"));

        if (languageIndex == 0) {
            fileMaker.createFile(Path.of("LexueFolder", "code.cpp"));
            fileMaker.writeFile(codeText.getText(), Path.of("LexueFolder/code.cpp"));
        }
        else if (languageIndex == 1) {
            fileMaker.createFile(Path.of("LexueFolder", "code.java"));
            fileMaker.writeFile(codeText.getText(), Path.of("LexueFolder/code.java"));
        }
        else if (languageIndex == 2) {
            fileMaker.createFile(Path.of("LexueFolder", "code.js"));
            fileMaker.writeFile(codeText.getText(), Path.of("LexueFolder/code.js"));
        }
        else if (languageIndex == 3) {
            fileMaker.createFile(Path.of("LexueFolder", "code.py"));
            fileMaker.writeFile(codeText.getText(), Path.of("LexueFolder/code.py"));
        }
    }


    private CompareResult RunCode(int languageIndex) throws IOException {

        if (languageIndex == 0) {
            return submiter.get().commitTest("C++", "LexueFolder\\code.cpp",
                    "LexueFolder\\code.exe", "output.txt");
        }
        if (languageIndex == 1) {
            return submiter.get().commitTest("Java", "LexueFolder\\code.java",
                    "LexueFolder\\code.class", "output.txt");
        }
        if (languageIndex == 2) {
            return submiter.get().commitTest("Python", "LexueFolder\\code.py",
                    "", "output.txt");
        }
        if (languageIndex == 3) {
            return submiter.get().commitTest("Node", "LexueFolder\\code.js",
                    "", "output.txt");
        }

        return CompareResult.UNKNOWN_ERROR;
    }

}
