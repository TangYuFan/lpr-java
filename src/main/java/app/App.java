package app;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public class App extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception{

        //ui文件
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        //程序图标
        //stage.getIcons().add(new Image(getClass().getResourceAsStream("/logo.png")));
        stage.setTitle("rec");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.setMaxHeight(545.5);
        stage.setMaxWidth(860);
        stage.show();
        //程序关闭钩子
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                //关闭所有线程
                Platform.exit();
                System.exit(0);
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
