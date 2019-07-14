package cn.net.polyglot.common;

import cn.net.polyglot.controller.entity.Contact;
import cn.net.polyglot.controller.entity.Notify;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class DataManager {

    private static DataManager instance;

    public static DataManager get(){
        if(instance==null){
            synchronized (DataManager.class){
                if(instance==null){
                    instance=new DataManager();
                }
            }
        }
        return instance;
    }

    //联系人集合
    private ObservableList<Contact> contacts= FXCollections.observableArrayList();
    //消息人集合
    private ObservableList<Notify> notifies=FXCollections.observableArrayList();

    public  ObservableList<Contact> getContacts() {
        return contacts;
    }

    public  void addContact(Contact contact){
        contacts.add(contact);
    }
    public  void addContact(Contact... contact){
        contacts.addAll(contact);
    }

    public ObservableList<Notify> getNotifies() {
        return notifies;
    }
}
