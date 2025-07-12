import java.util.Date;

public class Main {
    public static void main(String[] args) {
        EmployeeInterface employee = new Employee("David");

        employee.start(new Date());
        employee.work();



        TeamMember tm1 = new TeamMember(employee);
        tm1.start(new Date());
        tm1.work();
        tm1.colaborate();

 
        tm1.terminate(new Date());

        EmployeeInterface employee2 = new Employee("Ricardo");
        TeamMember tm = new TeamMember(employee2);
        tm.start(new Date());
        tm.work();
        tm.colaborate();

        tm.terminate(new Date());

        EmployeeInterface employee3 = new Employee("João");
        TeamLeader tl = new TeamLeader(employee3);
        tl.start(new Date());
        tl.work();
        tl.plan();

   

        tl.terminate(new Date());

        EmployeeInterface employee4 = new Employee("Manuel");
        Manager m = new Manager(employee4);
        m.start(new Date());
        m.work();
        m.manage();

  

        m.terminate(new Date());
    }
}