import java.util.ArrayList;
import java.util.List;

public class GnrlDataBase {
    
    private final S_Database DB;
    private final P_Registos Registos;

    public GnrlDataBase(S_Database DB, P_Registos Registos) {
        this.DB = DB;      
        this.Registos = Registos;    
    }

    public void addEmployee(EmployeeAdapter emp) {
        if (checkEmployee(emp.getCode())) {
            System.out.println("Nao e possivel adicionar!");
        } else {
            DB.addEmployee(new S_Employee(emp.getName(), emp.getCode(), emp.getSalary()));
        }

    }

    public void deleteEmployee(long code) {
        DB.deleteEmployee(code);
        Registos.remove((int) code);
    }

    public boolean checkEmployee(long code) {
        List<EmployeeAdapter> employees = new ArrayList<>();
        for (P_Empregado emp: Registos.listaDeEmpregados()) {
            employees.add(emp.empregadoToEmployee());
        }
        
        for (EmployeeAdapter emp: employees) {
            if (emp.getCode() == code) return true;
        }

        for (S_Employee emp: DB.getAllEmployees()) {
            if (emp.getEmpNum() == code) return true;
        }
        
        return false;
    }

    public S_Employee[] getAllEmployees(){
        S_Employee[] employees = new S_Employee[DB.getAllEmployees().length + Registos.listaDeEmpregados().size()];
        for (int i = 0; i < DB.getAllEmployees().length; i++){
            employees[i] = DB.getAllEmployees()[i];
        }
        for (int i = 0; i < Registos.listaDeEmpregados().size(); i++){
            P_Empregado empregado = Registos.listaDeEmpregados().get(i);
            S_Employee employee = new EmployeeAdapter(empregado);
            employees[i + DB.getAllEmployees().length] = employee;
        }
        return employees;
    }

    



}
