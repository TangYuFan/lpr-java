package app.javafx.utils;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class LogUtil {

    private static int logCount = 0;

    public static void log(TextArea resLog,String msg){
        logCount++;
        Platform.runLater(()->{
            resLog.appendText(msg+"\n");
            if(logCount>=30){
                resLog.setText("");
                logCount=0;
            }
        });

    }

    public static void label(Label resLog, String msg){
        Platform.runLater(()->{
            resLog.setText(msg);
        });
    }

    public static void text(TextField resLog, String msg){
        Platform.runLater(()->{
            resLog.setText(msg);
        });
    }

    public static void clear(TextArea resLog){
        Platform.runLater(()->{
            resLog.clear();
        });
    }

}
