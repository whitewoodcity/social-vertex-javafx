package cn.net.polyglot;

import cn.net.polyglot.controller.LoginController;
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
    primaryStage.setTitle("易信");
    primaryStage.setAlwaysOnTop(true);
    primaryStage.setResizable(false);
    FXMLLoader loader=new FXMLLoader(ClassLoader.getSystemResource("fxml/login.fxml"));
    Parent parent = loader.load();
    LoginController controller=loader.getController();
    controller.setStage(primaryStage);
    Scene scene = new Scene(parent,400,300);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
