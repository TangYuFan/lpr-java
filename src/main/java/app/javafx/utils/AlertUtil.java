package app.javafx.utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class AlertUtil {


    public static void alert(String msg){
        Platform.runLater(()->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(msg);
            alert.setHeaderText(null);
            alert.setTitle(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.show();
        });
    }

}
