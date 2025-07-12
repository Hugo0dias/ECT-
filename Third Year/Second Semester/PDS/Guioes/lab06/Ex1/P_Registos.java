
import java.util.ArrayList;
import java.util.List;

public class P_Registos {
    // Data elements
    private final ArrayList<P_Empregado> empregados; // Stores the employees

    public P_Registos() {
        empregados = new ArrayList<>();
    }

    public void insere(P_Empregado emp) {
        empregados.add(emp);
    }

    public void remove(int codigo) {
        for (P_Empregado empregado : empregados) {
            if (empregado.codigo() == codigo){
                empregados.remove(empregado);
                break;
            }
        }
    }

    public boolean isEmpregado(int codigo) {
        for (P_Empregado empregado : empregados) {
            if (empregado.codigo() == codigo){
                return true;
            }
        }
        return false;
    }
    public List<P_Empregado> listaDeEmpregados() {
        return empregados;
    }
}