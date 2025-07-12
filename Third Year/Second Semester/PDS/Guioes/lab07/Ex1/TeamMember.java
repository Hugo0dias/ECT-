import java.util.Date;

public class TeamMember extends EmployeeDecorator {

    public TeamMember(EmployeeInterface employee) {
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

    public void colaborate() {
        System.out.println("Team member " + super.getName() + " is colaborating.");
    }

}