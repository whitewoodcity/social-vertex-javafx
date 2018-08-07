package cn.net.polyglot.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.HttpURLConnection;

public class RegisterController {

    public TextField account;
    public PasswordField psd;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void doBack(MouseEvent mouseEvent) {
        FXMLLoader loader=new FXMLLoader(ClassLoader.getSystemResource("fxml/login.fxml"));
        try {
            Parent parent=loader.load();
            LoginController controller=loader.getController();
            controller.setStage(stage);
            Scene scene=new Scene(parent,400,300);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doRegister(ActionEvent actionEvent) {

    }
}
