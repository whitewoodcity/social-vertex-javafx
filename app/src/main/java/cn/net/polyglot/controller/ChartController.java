package cn.net.polyglot.controller;

import cn.net.polyglot.common.AbsController;
import cn.net.polyglot.common.Layout;
import cn.net.polyglot.controller.adapter.ChatCell;
import cn.net.polyglot.controller.entity.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

@Layout("fxml/chart_view.fxml")
public class ChartController extends AbsController {
    public Text name;
    public ListView<Message> msgList;
    public TextArea msgEditor;

    public ObservableList<Message> msg= FXCollections.observableArrayList(
            new Message("aaaaaadsadadadasdasdas",true),
            new Message("aaaaaadsadadadasdasdas",false),
            new Message("aaaaaadsadadadasdasdas",false),
            new Message("aaaaaadsadadadasdasdas",false),
            new Message("aaaaaadsadadadasdasdas",true),
            new Message("aaaaaadsadadadasdasdas",true),
            new Message("aaaaaadsadadadasdasdas",false),
            new Message("aaaaaadsadadadasdasdas",true)

    );



    @Override
    protected void onCreated(URL location, ResourceBundle resources) {
        msgList.setCellFactory(param -> new ChatCell());
        msgList.setEditable(false);
        msgList.setItems(msg);
        msgEditor.focusedProperty().addListener((observable, oldValue, newValue) -> {
            Region region= (Region) msgEditor.lookup(".content");
            region.setStyle("-fx-background-color:"+(newValue?" white":"#F5F5F5"));

        });
    }

}
