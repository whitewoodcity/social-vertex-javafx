package cn.net.polyglot.common;

import cn.net.polyglot.controller.entity.Message;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class MessageCell extends ListCell<Message> {

    private TextArea ta;

    public MessageCell() {
        this.ta = new TextArea();
        ta.setEditable(false);
        ta.setWrapText(true);
        ta.getStyleClass().add("copyLabel");
    }

    @Override
    protected void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        }else {
            BorderPane root=new BorderPane();
            HBox msgLine=new HBox();
            msgLine.prefWidthProperty().bind(widthProperty().divide(2));
            msgLine.setPadding(new Insets(5));
            StackPane sp=new StackPane();
            sp.setPrefWidth(40);
            ImageView head=new ImageView();
            head.setImage(new Image("icons/users.png"));
            head.setFitHeight(40);
            head.setFitWidth(40);
            sp.getChildren().add(head);

            Label msg=new Label(item.getMsg());
            msg.maxWidthProperty().bind(widthProperty().divide(2));
            msg.setFont(new Font(16));
            msg.setStyle("-fx-text-fill: black;" +
                    "-fx-wrap-text: true;" +
                    "-fx-background-color: transparent;" +
                    "-fx-border-color: transparent;");
            msgLine.setOnMouseEntered(event -> {
                ta.setText(msg.getText());
                ta.setPrefHeight(msg.getHeight());
                ta.setPrefWidth(msg.getWidth());
                if(item.isMine()){
                    msgLine.getChildren().set(0,ta);
                }else {
                    msgLine.getChildren().set(1,ta);
                }
            });
            msgLine.setOnMouseExited(event -> {
                if(item.isMine()){
                    msg.setAlignment(Pos.TOP_RIGHT);
                    msgLine.getChildren().set(0,msg);
                }else {
                    msg.setAlignment(Pos.TOP_LEFT);
                    msgLine.getChildren().set(1,msg);
                }
            });
            if(item.isMine()){
                msg.setAlignment(Pos.TOP_RIGHT);
                msgLine.setAlignment(Pos.TOP_RIGHT);
                msgLine.getChildren().addAll(msg,sp);
            }else {
                msg.setAlignment(Pos.TOP_LEFT);
                msgLine.setAlignment(Pos.TOP_LEFT);
                msgLine.getChildren().addAll(sp,msg);
            }
            root.setCenter(msgLine);
            setGraphic(root);
        }
    }
}
