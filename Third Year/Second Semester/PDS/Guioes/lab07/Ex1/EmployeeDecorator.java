import java.util.Date;

public class EmployeeDecorator implements EmployeeInterface{
    protected EmployeeInterface employee;

    public EmployeeDecorator(EmployeeInterface employee){
        this.employee = employee;
    }

    @Override
    public void start(Date date) {
        this.employee.start(date);
    }

    @Override
    public void terminate(Date date) {
        this.employee.terminate(date);
    }

    @Override
    public void work() {
        this.employee.work();
    }

    @Override
    public String getName() {
        return this.employee.getName();
    }
}