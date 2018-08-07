package com.im.social.controller;

import com.im.social.common.ContactCell;
import com.im.social.common.MessageCell;
import com.im.social.entity.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    public ImageView msg_icon;
    public ImageView contact_icon;
    public ListView<String> lv;
    public ListView<Message> msgList;

    private Image normalMsg=new Image("icons/msg_32.png");
    private Image enterMsg=new Image("icons/msg_enter_32.png");
    private Image normalContact=new Image("icons/contact_32.png");
    private Image enterContact=new Image("icons/contact_enter_32.png");
    private ObservableList<String> strList= FXCollections.observableArrayList("1","2","3","1","2","3","1","2","3"
    ,"1","2","3","1","2","3","1","2","3","1","2","3","1","2","3","1","2","3");
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lv.setCellFactory(param -> new ContactCell());
        lv.setItems(strList);
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
