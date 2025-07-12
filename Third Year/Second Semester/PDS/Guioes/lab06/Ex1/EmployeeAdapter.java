public class EmployeeAdapter extends S_Employee implements Interface{ // Ensure Interface has the correct method signatures
    
    private final P_Empregado Employee;

    public EmployeeAdapter(P_Empregado Employee) {
        super(Employee.nome() + " " +  Employee.apelido(), Employee.codigo(), Employee.salario());
        this.Employee = Employee;
    }

    @Override
    public String getName() {
        return Employee.nome() + Employee.apelido(); 
    }

    @Override
    public long getCode() {
        return Employee.codigo(); 
    }

    @Override
    public double getSalary() { 
        return Employee.salario();
    }


    
}
