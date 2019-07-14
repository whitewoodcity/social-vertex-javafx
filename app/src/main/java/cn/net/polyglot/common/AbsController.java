package cn.net.polyglot.common;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public abstract class AbsController implements Initializable {

    private Parent root;

    public AbsController() {
        Layout layout = getClass().getAnnotation(Layout.class);
        if (layout == null) {
            throw new RuntimeException("请使用Layout指定布局文件");
        }
        String fxml = layout.value();
        if (fxml.trim().isEmpty()) {
            throw new RuntimeException("未指定布局文件名称");
        }
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(fxml));
        loader.setController(null);
        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        onCreated(location, resources);

    }

    protected void onCreated(URL location, ResourceBundle resources) {

    }
    public Parent getRoot() {
        return root;
    }


}
