package cn.net.polyglot.views.mains.states;

import cn.net.polyglot.common.MessageCell;
import cn.net.polyglot.controller.entity.Message;
import cn.net.polyglot.views.mains.MainViewContext;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class ChartViewState extends MainViewState {

    private BorderPane root;


    public ChartViewState(MainViewContext context) {
        super(context);
    }

    @Override
    public Node getView() {
        if(root==null){
            FXMLLoader loader=new FXMLLoader(ClassLoader.getSystemResource("fxml/chart_view.fxml"));
            try {
                Parent parent=loader.load();
                root= (BorderPane) parent;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return root;
    }


}
