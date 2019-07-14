package cn.net.polyglot;

import cn.net.polyglot.controller.LoginController;
import cn.net.polyglot.net.AppService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

  public static void main(String[] args) {
    System.getProperties().setProperty("vertx.disableDnsResolver","true");
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("聊天室");
    primaryStage.setResizable(false);
    LoginController controller=new LoginController();
    controller.setStage(primaryStage);
    Scene scene = new Scene(controller.getRoot(),400,300);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  @Override
  public void stop() throws Exception {
    AppService.get().disconnect();
  }
}
