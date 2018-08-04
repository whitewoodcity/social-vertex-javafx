package com.im.social.common;

import com.im.social.entity.Message;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MessageCell extends ListCell<Message> {

    @Override
    protected void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        }else {
            BorderPane root=new BorderPane();
            HBox msgLine=new HBox();
            msgLine.setPrefWidth(getPrefWidth()/2);
            msgLine.setPadding(new Insets(5));
            StackPane sp=new StackPane();
            sp.setPrefWidth(40);
            ImageView head=new ImageView();
            head.setImage(new Image("icons/users.png"));
            head.setFitHeight(40);
            head.setFitWidth(40);
            sp.getChildren().add(head);
            TextFlow tf;

            TextField msg=new TextField(item.getMsg());
            msg.setEditable(false);

            msg.setFont(new Font(16));
            msg.setStyle("-fx-text-fill: black;" +
                    "-fx-wrap-text: true;" +
                    "-fx-background-color: transparent;" +
                    "-fx-border-color: transparent;");
            if(item.isMine()){
                msgLine.setAlignment(Pos.CENTER_RIGHT);
                msgLine.getChildren().addAll(msg,head);
            }else {
                msgLine.setAlignment(Pos.CENTER_LEFT);
                msgLine.getChildren().addAll(head,msg);
            }
            root.setCenter(msgLine);
            setGraphic(root);
        }
    }
}
