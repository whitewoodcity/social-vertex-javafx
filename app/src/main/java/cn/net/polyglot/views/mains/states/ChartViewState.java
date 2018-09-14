package cn.net.polyglot.views.mains.states;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class ChartViewState implements IMainViewState{

    private BorderPane root;

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
