package app.javafx.controller;

import app.javafx.lpr.xxxRange;
import app.javafx.utils.FpsUtil;
import app.javafx.utils.LogUtil;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import app.javafx.utils.AlertUtil;
import app.javafx.utils.ConfigUtil;
import org.bytedeco.javacv.*;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class Controller implements Initializable {

    //视频流地址
    @FXML
    private TextField videoAddr;

    //帧图片显示区域
    @FXML
    private ImageView videImg;

    //FPS显示区域
    @FXML
    private Label fps;

    //日志显示区域
    @FXML
    private TextArea logRes;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    //打开视频
    public void videoOpen() throws Exception {
        //视频流地址
        String addr = videoAddr.getText();
        if(addr==null){
            AlertUtil.alert("视频流地址错误！");
            return;
        }
        if(ConfigUtil.isVideoOpen){
            AlertUtil.alert("视频流地址已连接！");
            return;
        }
        ConfigUtil.isVideoOpen=true;

        //读取每一帧保存到缓存
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(addr);
        grabber.setImageHeight(ConfigUtil.videoHeight);
        grabber.setImageWidth(ConfigUtil.videoWeight);
        grabber.setFrameRate(60);//最高拉取60帧
        grabber.setTimeout(1000);
        Java2DFrameConverter converter = new Java2DFrameConverter();
        new Thread(()->{
            try {
                grabber.start();
                Frame frame = null;
                final AtomicReference<WritableImage> ref = new AtomicReference<>();
                BufferedImage img = null;
                while (videImg.isVisible()&&(frame=grabber.grab())!=null&& ConfigUtil.isVideoOpen) {
                    if (frame.image!=null) {
                        //是否开启实时检测
                        if(ConfigUtil.isRealTimeDetection){
                            xxxRange.huaRange(frame);
                        }
                        //转化为javafx能显示的image
                        img = converter.convert(frame);
                        ref.set(SwingFXUtils.toFXImage(img, ref.get()));
                        img.flush();
                        Platform.runLater(()->videImg.setImage(ref.get()));
                        FpsUtil.add();
                    }
                }
                grabber.close();
                ConfigUtil.isVideoOpen=false;
            } catch (FrameGrabber.Exception e) {
                ConfigUtil.isVideoOpen=false;
            }
        }).start();

        LogUtil.log(logRes,"媒体文件打开！");

        //显示fps
        new Thread(()->{
            try {
                while (ConfigUtil.isVideoOpen){
                    Thread.sleep(FpsUtil.timeRange);
                    LogUtil.label(fps,FpsUtil.getFps());
                    LogUtil.log(logRes,"帧图尺寸:"+videImg.getFitWidth()+"*"+videImg.getFitHeight());
                }
                LogUtil.label(fps,"00.00");
            }catch (Exception e){}
        }).start();

    }



    //关闭视频
    public void videoClose(){
        ConfigUtil.isVideoOpen=false;
        LogUtil.log(logRes,"媒体文件关闭！");
    }


    //清除日志
    public void clearLog(){
        LogUtil.clear(logRes);
    }


    //开启实时检测
    public void openRealTimeDetection(){
        ConfigUtil.isRealTimeDetection=true;
    }

    //关闭实时检测
    public void closeRealTimeDetection(){
        ConfigUtil.isRealTimeDetection=false;
    }

}
