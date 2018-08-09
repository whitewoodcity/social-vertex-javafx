package cn.net.polyglot.controller;

import cn.net.polyglot.config.Constants;
import cn.net.polyglot.util.Util;
import io.vertx.core.Vertx;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;

public class RegisterController {

    public TextField account;
    public PasswordField psd;
    public BorderPane registView;
    private BorderPane loginView;
    public Label errorInfo;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLoginView(BorderPane loginView) {
        this.loginView = loginView;
    }

    public void doBack(MouseEvent mouseEvent) {
        FXMLLoader loader=new FXMLLoader(ClassLoader.getSystemResource("fxml/login.fxml"));
        try {
            Parent parent=loader.load();
            LoginController controller=loader.getController();
            controller.setStage(stage);
            stage.getScene().setRoot(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doRegister(ActionEvent actionEvent) {
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
        client.put(Constants.DEFAULT_HTTP_PORT, Constants.SERVER, "/"+Constants.USER+"/"+Constants.REGISTER)
                .timeout(30000)
                .as(BodyCodec.jsonObject())
                .sendJsonObject(new JsonObject()
                        .put(Constants.ID, user)
                        .put(Constants.PASSWORD, Util.md5(password))
                        .put(Constants.VERSION, Constants.CURRENT_VERSION), ar -> {
                    if (ar.succeeded()) {
                        HttpResponse<JsonObject> response = ar.result();
                        System.out.println(response.body());
                        JsonObject jsonObject=response.body();
                        if(jsonObject.getBoolean("register",false)){
                            Platform.runLater(()->{
                                stage.getScene().setRoot(loginView);
                            });
                        }else {
                            Platform.runLater(()->{
                                stage.getScene().setRoot(registView);
                                errorInfo.setText(jsonObject.getString("info",""));
                            });
                        }

                    } else {
                        ar.cause().printStackTrace();
                        Platform.runLater(()->{
                            stage.getScene().setRoot(registView);
                        });
                    }
                });
    }
}
