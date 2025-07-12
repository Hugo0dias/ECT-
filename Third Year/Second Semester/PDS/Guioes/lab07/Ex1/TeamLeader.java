import java.util.Date;

public class TeamLeader extends EmployeeDecorator {

    public TeamLeader(EmployeeInterface employee) {
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

    public void plan() {
        System.out.println("Team leader " + super.getName() + " is planning.");
    }
}