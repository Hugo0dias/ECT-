import java.util.Date;

public class Manager extends EmployeeDecorator {

    public Manager(EmployeeInterface employee){
        super(employee);
    }

    @Override
    public void start(Date date) {
        super.start(date);
    }

    @Override
    public void terminate(Date date) {
        super.terminate(date);
    }

    @Override
    public void work() {
        super.work();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public void manage() {
        System.out.println("Manager " + super.getName() + " is managing.");
    }

}