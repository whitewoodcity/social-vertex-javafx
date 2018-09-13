package cn.net.polyglot.controller;

import cn.net.polyglot.common.ContactCell;
import cn.net.polyglot.common.DataManager;
import cn.net.polyglot.common.MessageCell;
import cn.net.polyglot.entity.Contact;
import cn.net.polyglot.entity.Message;
import cn.net.polyglot.net.AppService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    public ImageView msg_icon;
    public ImageView contact_icon;
    public ListView<Contact> lv;
    public ListView<Message> msgList;
    public TextArea msgEditor;

    private Image normalMsg=new Image("icons/msg_32.png");
    private Image enterMsg=new Image("icons/msg_enter_32.png");
    private Image normalContact=new Image("icons/contact_32.png");
    private Image enterContact=new Image("icons/contact_enter_32.png");
    private ObservableList<Message> msgs= FXCollections.observableArrayList(new Message("的撒旦撒打算的撒",true),
            new Message("的撒旦撒打算的撒dsad",false),
            new Message("的撒旦撒打算的撒",true),
            new Message("的撒旦撒打算的撒",true),
            new Message("的撒旦撒打算的撒",true),
            new Message("的撒旦撒打算的撒dsadafdsfsdfdsfdsfdsfdsffdsfsfsfdsfdsfdsfdsfdsfdsfdsfsfsfsfsdfdsfdsfdsfdsfsdfdsdsfdsfdsfsdfsdfdsfsdfdsfdsfdsfdsfsdfdsfdsfdsfds",false),
            new Message("的撒旦撒打算的撒dsada",false),
            new Message("的撒旦撒打算的撒d",true),
            new Message("的撒旦撒打算的撒dsadas",false),
            new Message("的撒旦撒打算的撒",true),
            new Message("的撒旦撒打算的撒",true),
            new Message("的撒旦撒打算的撒dsa",false),
            new Message("的撒旦撒打算的撒",true),
            new Message("的撒旦撒打算的撒",true)
            );

    private Stage mainStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
        this.mainStage.setOnCloseRequest(event -> {
            AppService.get().disconnect();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        msgEditor.focusedProperty().addListener((observable, oldValue, newValue) -> {
            Region region= (Region) msgEditor.lookup(".content");
            region.setStyle("-fx-background-color:"+(newValue?" white":"#F5F5F5"));

        });
        lv.setCellFactory(param -> new ContactCell());
        lv.setItems(DataManager.getContacts());
        msgList.setCellFactory(param -> new MessageCell());
        msgList.setEditable(false);
        msgList.setItems(msgs);

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

}
