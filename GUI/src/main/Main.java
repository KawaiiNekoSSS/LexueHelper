package main;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

    public static void main(String[] args) {
        launch(args);
    }
}
