package Ex2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;

public class ContactText implements ContactsStorageInterface{
    private String fileName;

    public ContactText(String fileName){
        this.fileName = fileName;
    }

    @Override
    public List<Contact> loadContacts(){
        List<Contact> contacts = new ArrayList<>();
        List<String> lines = new ArrayList<>();

        try{
            lines = Files.readAllLines(Paths.get(fileName));
            for(String line: lines){
                String[] contact = line.split(" - ");

                try{
                    String name = contact[0];
                    int n = Integer.parseInt(contact[1]);
                    Contact c = new Contact(name, n);
                    contacts.add(c);
                }catch (Exception e){
                    System.err.println("Invalid number: " + contact[1]);
                }

            }
        }catch (Exception e){
            System.err.println("Error Reading file"+ e.getMessage());
        }

        return contacts;
    }


    @Override
    public boolean saveContacts(List<Contact> list){
        try{
            File f = new File("Ex2/savedContactsText.txt");
            FileWriter fw = new FileWriter(f);

            for(Contact c : list){
                fw.write(c.getName() + " - " + c.getNumber() + "\n");
            }
            fw.close();

            return true;
        }catch (Exception e){
            System.err.println("Error: "+ e);
            System.exit(1);
        }

        return false;
    }
}
