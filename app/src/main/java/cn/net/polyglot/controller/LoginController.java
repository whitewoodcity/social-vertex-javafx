package cn.net.polyglot.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public TextField account;
    public PasswordField psd;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void doRegister(ActionEvent actionEvent) {
        FXMLLoader loader=new FXMLLoader(ClassLoader.getSystemResource("fxml/register.fxml"));
        try {
            Parent parent=loader.load();
            RegisterController controller=loader.getController();
            controller.setStage(stage);
            Scene scene=new Scene(parent,400,300);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doLogin(ActionEvent actionEvent) {

    }
}
