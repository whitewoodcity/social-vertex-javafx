package cn.net.polyglot.controller;

import cn.net.polyglot.config.Constants;
import cn.net.polyglot.util.Util;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
  public TextField account;
  public PasswordField psd;
  public BorderPane loginView;

  private Stage stage;

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public void doRegister(ActionEvent actionEvent) {
    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("fxml/register.fxml"));
    try {
      Parent parent = loader.load();
      RegisterController controller = loader.getController();
      controller.setStage(stage);
      stage.getScene().setRoot(parent);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void doLogin(ActionEvent actionEvent) {
    StackPane root = new StackPane();
    ImageView head = new ImageView();
    head.setImage(new Image("icons/users.png"));
    head.setFitWidth(80);
    head.setFitHeight(80);
    root.setAlignment(Pos.CENTER);
    root.getChildren().add(head);
    stage.getScene().setRoot(root);
    String user = account.getText();
    String password = psd.getText();
    WebClient client = WebClient.create(Vertx.vertx());
    client.put(Constants.DEFAULT_HTTP_PORT, Constants.SERVER, "/"+Constants.USER+"/"+Constants.LOGIN)
        .timeout(30000)
        .as(BodyCodec.jsonObject())
        .sendJsonObject(new JsonObject()
            .put(Constants.TYPE, Constants.USER)
            .put(Constants.SUBTYPE, Constants.LOGIN)
            .put(Constants.ID, user)
            .put(Constants.PASSWORD, Util.md5(password))
            .put(Constants.VERSION, Constants.CURRENT_VERSION), ar -> {
          if (ar.succeeded()) {
            HttpResponse<JsonObject> response = ar.result();
            System.out.println(response.body());
            System.out.println(Thread.currentThread());
            Platform.runLater(()->{
              stage.getScene().setRoot(loginView);//返回值在Vert.x的eventloop中获取，最好用platform.runlater做一个转发
            });
          } else {
            stage.getScene().setRoot(loginView);
          }
        });
  }
}
