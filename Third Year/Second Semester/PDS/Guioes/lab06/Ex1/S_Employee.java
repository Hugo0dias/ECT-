

// Sweets
public class S_Employee {
    private final String name;
    private final long emp_num;
    private final double salary;
    
    public S_Employee(String name, long emp_num, double salary) {
        this.name = name;
        this.emp_num = emp_num;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public long getEmpNum() {
        return emp_num;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee [name=" + name + ", emp_num=" + emp_num + ", salary=" + salary + "]";
    }
}