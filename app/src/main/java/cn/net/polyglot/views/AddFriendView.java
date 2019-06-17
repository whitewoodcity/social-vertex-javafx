package cn.net.polyglot.views;

import cn.net.polyglot.config.Constants;
import cn.net.polyglot.net.HttpService;
import io.vertx.core.json.JsonObject;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.dialog.ExceptionDialog;

public class AddFriendView {

    private Stage stage;
    private TextField tf_keywords;
    private Label errorText;

    public AddFriendView(){
        stage=new Stage();
        stage.setTitle("添加好友");
        Scene scene=new Scene(createView(),400,100);
        stage.setScene(scene);

    }

    private BorderPane createView(){
        BorderPane root=new BorderPane();
        GridPane gridPane=new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        Label searchTxt=new Label("用户名:");
        tf_keywords=new TextField();
        tf_keywords.setPromptText("输入联系人号");
        Button send=new Button();
        send.setOnAction(this::doSearch);
        send.setText("搜索");
        errorText=new Label();
        errorText.setStyle("-fx-text-fill: red");
        gridPane.add(searchTxt,0,0);
        gridPane.add(tf_keywords,1,0,2,1);
        gridPane.add(send,3,0);
        gridPane.add(errorText,0,1);
        root.setCenter(gridPane);
        return root;
    }

    public void show(){
        stage.show();
    }

    private void doSearch(ActionEvent event){
        String keyword=tf_keywords.getText();
        if(keyword.isEmpty()){
            errorText.setText("请输入用户id值");
            return;
        }else {
            errorText.setText("");
        }
        HttpService.get().put("/search/info",
                new JsonObject()
                    .put(Constants.TYPE,"search")
                    .put(Constants.SUBTYPE,"info")
                    .put(Constants.KEYWORD,keyword)
                    .put(Constants.VERSION,Constants.CURRENT_VERSION),
                success->{

                },fail-> new ExceptionDialog(fail.cause()).show());
    }
}
