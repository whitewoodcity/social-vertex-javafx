package cn.net.polyglot.common;

import cn.net.polyglot.controller.entity.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManager {

    private static ObservableList<Contact> contacts= FXCollections.observableArrayList();

    public static ObservableList<Contact> getContacts() {
        return contacts;
    }

    public static void addContact(Contact contact){
        contacts.add(contact);
    }
    public static void addContact(Contact... contact){
        contacts.addAll(contact);
    }
}
