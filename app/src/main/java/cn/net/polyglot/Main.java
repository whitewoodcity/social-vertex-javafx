package cn.net.polyglot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("易信");
    Parent parent = FXMLLoader.load(ClassLoader.getSystemResource("fxml/main.fxml"));
    Scene scene = new Scene(parent);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
