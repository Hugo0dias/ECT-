abstract class Employee {
	protected String name;
	public abstract String getName();
}

class Programmer extends Employee {
	public Programmer(String name) {
		this.name = name;
	}
	@Override
	public String getName() {
		return name;
	}
}

class EmployeeFactory {
	public static final String[] names = { "Mac", "Linux", "Win" };

	public static Employee getEmployee(String name) {
		for (int i = 0; i < names.length; i++) {
			if (names[i].equalsIgnoreCase(name)) {
				return new Programmer(name);
			}
		}
		return null;
	}
}

public class NullDemo {
	public static void main(String[] args) {

		Employee emp = EmployeeFactory.getEmployee("Mac");
		Employee emp2 = EmployeeFactory.getEmployee("Janela");
		Employee emp3 = EmployeeFactory.getEmployee("Linux");
		Employee emp4 = EmployeeFactory.getEmployee("Mack");

		System.out.println(emp.getName());
		System.out.println(emp2.getName());
		System.out.println(emp3.getName());
		System.out.println(emp4.getName());
	}
}
