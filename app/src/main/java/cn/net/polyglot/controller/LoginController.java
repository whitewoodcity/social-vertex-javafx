package cn.net.polyglot.controller;

import cn.net.polyglot.common.AbsController;
import cn.net.polyglot.common.DataManager;
import cn.net.polyglot.common.Layout;
import cn.net.polyglot.controller.entity.Contact;
import cn.net.polyglot.net.HttpService;
import cn.net.polyglot.util.AlertUtil;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
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

@Layout("fxml/login.fxml")
public class LoginController extends AbsController {
    public TextField account;
    public PasswordField psd;
    public BorderPane loginView;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void doRegister(ActionEvent actionEvent) {
        RegisterController controller=new RegisterController();
        controller.setStage(stage);
        controller.setLoginView(loginView);
        stage.getScene().setRoot(controller.getRoot());
    }

    public void doLogin(ActionEvent actionEvent) {
        String user = account.getText();
        String password = psd.getText();
        if (user.isEmpty() || password.isEmpty()) {
            AlertUtil.normalError("用户名和密码不能为空");
            return;
        }
        loginProcess(stage);

        HttpService.get().doLogin(user,password,
                success->{
                    System.out.println(success.body().toString());
                    Boolean login = success.body().getBoolean("login", false);
                    if (!login) {
                        stage.getScene().setRoot(loginView);
                        AlertUtil.error("登录", "登录失败", "用户名和密码不正确");
                        return;
                    }
                    JsonArray jsonArray = success.body().getJsonArray("friends");

                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject jo = jsonArray.getJsonObject(i);
                        Contact contact = new Contact();
                        contact.setId(jo.getString("id", null));
                        contact.setNickName(jo.getString("nickname", null));
                        DataManager.get().addContact(contact);
                    }
                    startMain();
                },
                ()->{
                    stage.getScene().setRoot(loginView);
                    AlertUtil.error("登录", "登录失败", "用户名和密码不正确");
                });

    }

    private void loginProcess(Stage stage) {
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
    }

    private void startMain(){
        Stage mainStage = new Stage();
        mainStage.setTitle("聊天室");
        MainController mainController=new MainController();
        Scene scene = new Scene(mainController.getRoot());
        mainStage.setScene(scene);
        mainStage.show();
        stage.close();
    }
}
