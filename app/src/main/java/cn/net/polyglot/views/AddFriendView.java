package cn.net.polyglot.views;

import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.BorderPane;

public class AddFriendView {


    public AddFriendView(){

    }

    public static void show(){
        Dialog dialog=new Dialog();
        DialogPane pane=new DialogPane();
        BorderPane root=new BorderPane();
        pane.setContent(root);
        dialog.setDialogPane(pane);
        dialog.setOnCloseRequest(event -> dialog.close());
        dialog.show();
    }
}
