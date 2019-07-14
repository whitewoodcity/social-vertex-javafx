package cn.net.polyglot.controller;

import cn.net.polyglot.common.AbsController;
import cn.net.polyglot.common.DataManager;
import cn.net.polyglot.common.Layout;
import cn.net.polyglot.controller.adapter.ContactCell;
import cn.net.polyglot.controller.adapter.MessageCell;
import cn.net.polyglot.controller.entity.Contact;
import cn.net.polyglot.controller.entity.Notify;
import cn.net.polyglot.views.AddFriendView;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

@Layout("fxml/main.fxml")
public class MainController extends AbsController {

    private static final int MSG_PANE=1;
    private static final int CONTACT_PANE=2;

    public ListView<Notify> lv;
    public ListView<Contact> lvContact;
    public StackPane mainView;

    private LogoController logoController;
    private ChartController chartController;



    private int currentPane=MSG_PANE;



    @Override
    protected void onCreated(URL location, ResourceBundle resources) {
        lv.setCellFactory(param -> new MessageCell());
        lv.setItems(DataManager.get().getNotifies());
        lvContact.setCellFactory(param -> new ContactCell());
        lvContact.setItems(DataManager.get().getContacts());
        logoController=new LogoController();
        chartController=new ChartController();
        mainView.getChildren().add(logoController.getRoot());

        lv.setOnMouseClicked(event -> {
            Notify notify=lv.getSelectionModel().getSelectedItem();
        });
        lvContact.setOnMouseClicked(event -> {
            Contact contact=lvContact.getSelectionModel().getSelectedItem();

        });
    }


    public void showAddFriendPane(MouseEvent mouseEvent) {
        new AddFriendView().show();
    }

    public void changeToMsg(MouseEvent mouseEvent) {
        if(currentPane==MSG_PANE){
            return;
        }
        currentPane=MSG_PANE;
        lv.setManaged(true);
        lv.setVisible(true);
        lvContact.setVisible(false);
        lvContact.setManaged(false);
    }

    public void changeToContact(MouseEvent mouseEvent) {
        if(currentPane==CONTACT_PANE){
            return;
        }
        currentPane=CONTACT_PANE;
        lvContact.setManaged(true);
        lvContact.setVisible(true);
        lv.setVisible(false);
        lv.setManaged(false);

    }
}
