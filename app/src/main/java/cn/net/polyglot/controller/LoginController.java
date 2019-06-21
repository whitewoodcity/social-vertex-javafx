package cn.net.polyglot.controller;

import cn.net.polyglot.common.DataManager;
import cn.net.polyglot.config.Constants;
import cn.net.polyglot.controller.entity.Contact;
import cn.net.polyglot.net.AppService;
import cn.net.polyglot.net.HttpService;
import cn.net.polyglot.util.AlertUtil;
import cn.net.polyglot.util.Util;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.dialog.ExceptionDialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
            controller.setLoginView(loginView);
            stage.getScene().setRoot(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doLogin(ActionEvent actionEvent) {
        String user = account.getText();
        String password = psd.getText();
        if (user.isEmpty() || password.isEmpty()) {
            AlertUtil.normalError("用户名和密码不能为空");
            return;
        }
        StackPane root = new StackPane();
        VBox vBox = new VBox();
        ImageView head = new ImageView();
        head.setImage(new Image("icons/users.png"));
        head.setFitWidth(80);
        head.setFitHeight(80);
        Label text = new Label("正在登录服务器...");
        text.setFont(new Font(14));
        vBox.getChildren().addAll(head, text);
        vBox.setAlignment(Pos.CENTER);
        root.getChildren().add(vBox);
        root.setAlignment(Pos.CENTER);
        stage.getScene().setRoot(root);


        HttpService.get().put(new JsonObject()
                        .put(Constants.TYPE, Constants.USER)
                        .put(Constants.SUBTYPE, Constants.LOGIN)
                        .put(Constants.ID, user)
                        .put(Constants.PASSWORD, Util.md5(password))
                        .put(Constants.VERSION, Constants.CURRENT_VERSION),
                success -> Platform.runLater(() -> {
                    System.out.println("login response data:" + success.body().toString());
                    Boolean login=success.body().getBoolean("login",false);
                    if (!login) {
                        Platform.runLater(() -> {
                            stage.getScene().setRoot(loginView);
                            AlertUtil.error("登录", "登录失败", "用户名和密码不正确");
                        });
                        return;
                    }
                    Stage mainStage = new Stage();
                    mainStage.setTitle("易信");
//                mainStage.setAlwaysOnTop(true);

                    JsonArray jsonArray=success.body().getJsonArray("friends");
                    for (int i=0;i<jsonArray.size();i++){
                        JsonObject jo=jsonArray.getJsonObject(i);
                        Contact contact=new Contact();
                        contact.setId(jo.getString("id",null));
                        contact.setNickName(jo.getString("nickname",null));
                        DataManager.addContact(contact);
                    }

                    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("fxml/main.fxml"));
                    try {
                        Parent parent = loader.load();
                        MainController controller = loader.getController();
                        controller.setMainStage(mainStage);
                        Scene scene = new Scene(parent);
                        mainStage.setScene(scene);
                        mainStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        ExceptionDialog exceptionDialog = new ExceptionDialog(e);
                        exceptionDialog.show();
                    } finally {
                        stage.close();
                    }
                }), fail -> {
                    fail.cause().printStackTrace();
                    Platform.runLater(() -> {
                        stage.getScene().setRoot(loginView);
                        AlertUtil.error("登录", "登录失败", "用户名和密码不正确");
                    });
                });
//
//        AppService.get().doLogin(user,password,success -> {
//            if(success){
//                Stage mainStage=new Stage();
//                mainStage.setTitle("易信");
////                mainStage.setAlwaysOnTop(true);
//                FXMLLoader loader=new FXMLLoader(ClassLoader.getSystemResource("fxml/main.fxml"));
//                try {
//                    Parent parent = loader.load();
//                    MainController controller=loader.getController();
//                    controller.setMainStage(mainStage);
//                    Scene scene = new Scene(parent);
//                    mainStage.setScene(scene);
//                    mainStage.show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    ExceptionDialog exceptionDialog=new ExceptionDialog(e);
//                    exceptionDialog.show();
//                }finally {
//                    stage.close();
//                }
//            }else {
//                stage.getScene().setRoot(loginView);
//                AlertUtil.error("登录","登录失败","用户名和密码不正确");
//            }
//        });

    }
}
