package Ex3;

import java.util.ArrayList;
import java.util.Collection;

public class Teste {
    
    public static void main(String[] args) {
        ComandHist history = new ComandHist();
        App invoker = new App(history);
        Collection<String> collection = new ArrayList<>();

        Command addCommand = new AddComand<>(collection, "1");
        Command removeCommand = new RemoveComand<>(collection, "1");

        invoker.setCommand(addCommand);
        invoker.executeCommand();
        System.out.println(collection);

        invoker.setCommand(removeCommand);
        invoker.executeCommand();
        System.out.println(collection);

        Command addCommand2 = new AddComand<>(collection, "2");
        Command addCommand3 = new AddComand<>(collection, "3");
        Command addCommand4 = new AddComand<>(collection, "4");

        invoker.setCommand(addCommand3);
        invoker.executeCommand();

        invoker.undoCommand();
        System.out.println(collection);

        Command addCommand5 = new AddComand<>(collection, "1");

        invoker.setCommand(addCommand2);
        invoker.executeCommand();
        invoker.setCommand(addCommand4);
        invoker.executeCommand();
        invoker.setCommand(addCommand5);
        invoker.executeCommand();

        System.out.println(collection);

        invoker.undoCommand();
        System.out.println(collection);

        invoker.undoCommand();
        System.out.println(collection);

    }

}
