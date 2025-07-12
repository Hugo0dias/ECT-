public class Main {
    public static void main(String[] args) {
        
        S_Database db = new S_Database();
        S_Employee emp1 = new S_Employee("Hugo", 11, 1500);
        S_Employee emp2 = new S_Employee("David", 9, 2200);
        db.addEmployee(emp1);
        db.addEmployee(emp2);

        for (S_Employee emp: db.getAllEmployees()) {
            System.out.println("Name: " + emp.getName() + ", Employee number: " + emp.getEmpNum() + ", Salary: " + emp.getSalary());
        }

        db.deleteEmployee(9);

        for (S_Employee emp: db.getAllEmployees()) {
            System.out.println("Name: " + emp.getName() + ", Employee number: " + emp.getEmpNum() + ", Salary: " + emp.getSalary());
        }

        // Nº9 é eliminado

        System.out.println("-----------------------------------------------------");

        P_Registos reg = new P_Registos();
        P_Empregado empr1 = new P_Empregado("Manuel", "Antunes", 220, 2100);
        P_Empregado empr2 = new P_Empregado("Pedro", "Santos", 120, 800);
        reg.insere(empr1);
        reg.insere(empr2);

        for (P_Empregado emp: reg.listaDeEmpregados()) {
            System.out.println("Name: " + emp.nome() + ", Apelido: " + emp.apelido() + ", Employee number: " + emp.codigo() + ", Salary: " + emp.salario());
        }

        reg.remove(220);

        for (P_Empregado emp: reg.listaDeEmpregados()) {
            System.out.println("Name: " + emp.nome() + ", Apelido: " + emp.apelido() + ", Employee number: " + emp.codigo() + ", Salary: " + emp.salario());
        }

        // Nº220 é eliminado

        GnrlDataBase Enterprise = new GnrlDataBase(db, reg);
        Enterprise.deleteEmployee(11);
        P_Empregado Emp = new P_Empregado("Francisco", "Castro", 120, 1000);
        System.out.println(Enterprise.checkEmployee(120));
        Enterprise.deleteEmployee(120);
        System.out.println(Enterprise.checkEmployee(120));

        // Add and remove from general DB

        P_Empregado Emplo = new P_Empregado("Sofia", "Almeida", 12, 800);
        EmployeeAdapter Employ = new EmployeeAdapter(Emplo);
        Enterprise.addEmployee(Employ);

        S_Employee[] employees = Enterprise.getAllEmployees();
        for (S_Employee employee : employees) {
            System.out.println(employee);
        }
           
    }
}