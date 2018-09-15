package cn.net.polyglot.controller;

import cn.net.polyglot.config.Constants;
import cn.net.polyglot.net.HttpService;
import cn.net.polyglot.util.AlertUtil;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
        String user = account.getText();
        String password = psd.getText();
        if(user.isEmpty()||password.isEmpty()){
            errorInfo.setText("请填写用户名和密码");
            return;
        }
        StackPane root = new StackPane();
        VBox vBox=new VBox();
        ImageView head = new ImageView();
        head.setImage(new Image("icons/users.png"));
        head.setFitWidth(80);
        head.setFitHeight(80);
        Label text=new Label("注册中请稍后...");
        text.setFont(new Font(14));
        vBox.getChildren().addAll(head,text);
        vBox.setAlignment(Pos.CENTER);
        root.getChildren().add(vBox);
        root.setAlignment(Pos.CENTER);
        stage.getScene().setRoot(root);

        HttpService.get().put("/"+Constants.USER+"/"+Constants.REGISTER,
                new JsonObject()
                        .put(Constants.ID, user)
                        .put(Constants.PASSWORD, Util.md5(password))
                        .put(Constants.VERSION, Constants.CURRENT_VERSION),
                success->{
                    JsonObject jsonObject=success.body();
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
                },fail->{
                    fail.cause().printStackTrace();
                    Platform.runLater(()->{
                        stage.getScene().setRoot(registView);
                        errorInfo.setText("注册服务未成功");
                    });
                });
    }
}
