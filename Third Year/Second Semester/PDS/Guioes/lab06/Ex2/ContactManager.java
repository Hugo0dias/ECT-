package Ex2;

import java.util.*;
import java.io.*;

public class ContactManager implements ContactsInterface{
    private ContactsStorageInterface store;
    private List<Contact> contacts = new ArrayList<>();


    public ContactManager(ContactsStorageInterface store) {
        this.store = store;
    }


    @Override
    public void openAndLoad(ContactsStorageInterface store) {
        contacts = new ArrayList<>(store.loadContacts());
    }

    @Override
    public void saveAndClose() {
        if (!store.saveContacts(contacts)) {
            System.err.println("Error saving contacts");
        }   
    }

    @Override
    public void saveAndClose(ContactsStorageInterface store) {
        if (!store.saveContacts(contacts)) {
            System.err.println("Error saving contacts");
        }
    }

    @Override
    public boolean exist(Contact contact) {
        return contacts.contains(contact);
    }

    @Override
    public Contact getByName(String name) {
        for (Contact c : contacts) {
            if (c.getName().equals(name)) {
                return c;
            }
        }

        return null;
    }

    @Override
    public boolean add(Contact contact) {
        return !exist(contact) && contacts.add(contact);
    }

    @Override
    public boolean remove(Contact contact) {
        return contacts.remove(contact);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Contact c: contacts){
            sb.append(c.getName() + " - "+ c.getNumber() + "\n");
        }

        return sb.toString();
    }

}
