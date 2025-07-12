import java.util.Date;

public class Employee implements EmployeeInterface{
    private String name;
    private Date startDate;
    private Date terminationDate;

    public Employee(String name){
        this.name = name;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public void start(Date date){
        this.startDate = date;
        System.out.println("Employee " + this.name + " started on " + this.startDate);
    }

    @Override
    public void terminate(Date date) {
        this.terminationDate = date;
        System.out.println("Employee " + this.name + " terminated on " + this.terminationDate);
    }

    @Override
    public void work() {
        System.out.println("Employee " + this.name + " is working.");
    }
}