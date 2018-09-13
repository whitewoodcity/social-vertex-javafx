package cn.net.polyglot.util;

import javafx.scene.control.Alert;

public class AlertUtil {


    public static void error(String title,String headText, String msg){
        showALert(Alert.AlertType.ERROR,title,headText,msg);
    }

    public static void normalError(String msg){
        error("错误","错误",msg);
    }

    private static void showALert(Alert.AlertType type,String title,String headText, String msg){
        Alert alert=new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headText);
        alert.setContentText(msg);
        alert.show();
    }

}
