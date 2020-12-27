package main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.TimeModel;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * @author neko
 * @title: defaultControlle
 * @projectName LexueHelper
 * @description: 基本控制器
 * @date 2020/12/2317:46
 */

public class defaultController implements Initializable {

    //回调
    private Main main;
    public void setMain(Main main) {
        this.main = main;
    }


    @FXML
    private Label timeNow; //view:label
    private TimeModel timeModel = new TimeModel();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //双向绑定
        timeNow.textProperty().bindBidirectional(timeModel.timeProperty());
        //启动线程开始计时
        updateTime();
    }

    /**
     * 更新时间
     */
    private void updateTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        new Thread(() -> {
            while (true) {
                Platform.runLater(() -> timeModel.setTime(timeFormat.format(new Date())));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ignore) {
                }
            }
        }).start();
    }

    @FXML
    public void showLocalSubmitScene() throws IOException {
        main.showLocalSubmitScene();
    }
}


