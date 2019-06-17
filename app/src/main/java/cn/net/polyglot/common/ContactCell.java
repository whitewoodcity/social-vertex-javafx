package cn.net.polyglot.common;

import cn.net.polyglot.controller.entity.Contact;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class ContactCell extends ListCell<Contact> {


  @Override
  protected void updateItem(Contact item, boolean empty) {
    super.updateItem(item, empty);
    if (empty) {
      setText(null);
      setGraphic(null);
    } else {
      BorderPane pane = new BorderPane();
      HBox root = new HBox();
      root.setPrefHeight(50);
      StackPane sp = new StackPane();
      sp.setPrefWidth(40);
      ImageView head = new ImageView();
      head.setImage(new Image("icons/users.png"));
      head.setFitHeight(40);
      head.setFitWidth(40);
      sp.getChildren().add(head);
      GridPane content = new GridPane();
      content.setPadding(new Insets(5));
      content.setHgap(5);
      content.setVgap(5);
      content.setAlignment(Pos.CENTER);
      Label name = new Label(item.getNickName());
      Label msg = new Label(item.getNewMsg());
      content.add(name, 0, 0);
      content.add(msg, 0, 1);
      root.getChildren().addAll(sp, content);
      pane.setCenter(root);
      setGraphic(pane);
    }
  }
}
