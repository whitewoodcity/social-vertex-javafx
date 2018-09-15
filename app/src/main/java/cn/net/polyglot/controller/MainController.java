package cn.net.polyglot.controller;

import cn.net.polyglot.common.ContactCell;
import cn.net.polyglot.common.DataManager;
import cn.net.polyglot.common.MessageCell;
import cn.net.polyglot.controller.entity.Contact;
import cn.net.polyglot.controller.entity.Message;
import cn.net.polyglot.net.AppService;
import cn.net.polyglot.views.AddFriendView;
import cn.net.polyglot.views.mains.MainViewContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    public ImageView msg_icon;
    public ImageView contact_icon;
    public ListView<Contact> lv;
    public StackPane mainView;


    private Image normalMsg=new Image("icons/msg_32.png");
    private Image enterMsg=new Image("icons/msg_enter_32.png");
    private Image normalContact=new Image("icons/contact_32.png");
    private Image enterContact=new Image("icons/contact_enter_32.png");


    private Stage mainStage;
    private MainViewContext mainViewContext;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
        this.mainStage.setOnCloseRequest(event -> {
            AppService.get().disconnect();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lv.setCellFactory(param -> new ContactCell());
        lv.setItems(DataManager.getContacts());
        mainViewContext=new MainViewContext();
        mainViewContext.applyChartView();
        mainView.getChildren().add(mainViewContext.getView());

    }

    public void handlerMsgMouseEntered(MouseEvent mouseEvent) {
        msg_icon.setImage(enterMsg);
    }

    public void handlerMsgMouseExit(MouseEvent mouseEvent) {
        msg_icon.setImage(normalMsg);
    }

    public void handlerContactMouseEntered(MouseEvent mouseEvent) {
        contact_icon.setImage(enterContact);
    }

    public void handlerContactMouseExit(MouseEvent mouseEvent) {
        contact_icon.setImage(normalContact);
    }

    public void showAddFriendPane(MouseEvent mouseEvent) {
        AddFriendView.show();
    }
}
