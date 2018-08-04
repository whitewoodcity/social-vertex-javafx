package com.im.social.common;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class ContactCell extends ListCell<String> {


    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        }else {
            BorderPane pane=new BorderPane();
            HBox root=new HBox();
            root.setPrefHeight(50);
            StackPane sp=new StackPane();
            sp.setPrefWidth(40);
            ImageView head=new ImageView();
            head.setImage(new Image("icons/users.png"));
            head.setFitHeight(40);
            head.setFitWidth(40);
            sp.getChildren().add(head);
            GridPane content=new GridPane();
            content.setPadding(new Insets(5));
            content.setHgap(5);
            content.setVgap(5);
            content.setAlignment(Pos.CENTER);
            Label name=new Label("张三");
            Label msg=new Label("今天天气真好呀呀呀呀呀呀呀");
            content.add(name,0,0);
            content.add(msg,0,1);
            root.getChildren().addAll(sp,content);
            pane.setCenter(root);
            setGraphic(pane);
        }
    }
}
