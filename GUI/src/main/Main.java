package main;

import bit.local.stdcompare.CompareProcess;
import bit.local.stdcompare.CompareResult;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showDefaultWindow();
    }

    //显示主窗体主界面
    public void showDefaultWindow() throws IOException{

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("../resources/default.fxml"));
        GridPane root = (GridPane)loader.load();
        defaultController controller = loader.getController();
        controller.setMain(this);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("欢迎来到hhm的OJ");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    //显示本地提交界面
    public void showLocalSubmitScene() throws IOException{

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("../resources/LocalSubmit.fxml"));
        GridPane root = (GridPane)loader.load();
        localSubmitController controller = loader.getController();
        controller.setMain(this);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("本地提交测试");
        primaryStage.setScene(scene);
    }

    //显示对拍界面
    public void showDuiPaiScene() throws IOException{

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("../resources/DuiPai.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        duiPaiController controller = loader.getController();
        controller.setMain(this);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("对拍测试");
        primaryStage.setScene(scene);
    }

    // 打开结果窗体
    public void openResultWindow(CompareResult result) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("../resources/result.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage newStage = new Stage();
            resultWindowController controller = loader.getController();
            controller.init(this, newStage);

            Scene scene = new Scene(root);

            controller.setMessage(result);


            newStage.setScene(scene);
            newStage.setTitle("对拍结果");
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
