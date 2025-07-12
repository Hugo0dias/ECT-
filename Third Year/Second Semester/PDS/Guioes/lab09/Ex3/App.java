package Ex3;

public class App {
    
    private Command command;
    private ComandHist history = new ComandHist();

    public App(ComandHist history) {
        this.history = history;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        command.Execute();
        history.push(command);
    }

    public void undoCommand() {
        if (!history.isEmpty()) {
            Command undoCommand = history.pop();
            undoCommand.undo();
        }
    }

}
