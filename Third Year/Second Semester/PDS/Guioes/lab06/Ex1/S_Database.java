
import java.util.Vector;

// Sweets
public class S_Database { // Data elements
    private Vector<S_Employee> employees; // Stores the employees
    
    public S_Database() {
        employees = new Vector<>();
    }

    public boolean addEmployee(S_Employee employee) {
        try {
            employees.add(employee);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void deleteEmployee(long emp_num) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmpNum() == emp_num) {
                employees.remove(i);
                break;
            }
        }
    }

    public S_Employee[] getAllEmployees() {
        S_Employee[] employeesArray = new S_Employee[employees.size()];
        for (int i = 0; i < employees.size(); i++){
            employeesArray[i] = employees.get(i);
        }
        return employeesArray;
    }
}