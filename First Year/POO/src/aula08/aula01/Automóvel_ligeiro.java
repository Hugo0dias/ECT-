package aula08.aula01;
public class Automóvel_ligeiro extends Conjunto_veiculos{

    int num_quadro;
    int capacidade_bagageira;

    public Automóvel_ligeiro(String Matricula, String marca, String modelo, int Potencia, int num_quadro, int capacidade_bagageira) {
        super(Matricula, marca, modelo, Potencia);
        this.num_quadro = num_quadro;
        this.capacidade_bagageira = capacidade_bagageira;
    }


    public int getNum_quadro() {
        return this.num_quadro;
    }

    public void setNum_quadro(int num_quadro) {
        this.num_quadro = num_quadro;
    }

    public int getCapacidade_bagageira() {
        return this.capacidade_bagageira;
    }

    public void setCapacidade_bagageira(int capacidade_bagageira) {
        this.capacidade_bagageira = capacidade_bagageira;
    }


    @Override
    public String toString() {
        return "{" +
            " num_quadro='" + getNum_quadro() + "'" +
            ", capacidade_bagageira='" + getCapacidade_bagageira() + "'" +
            "}";
    }
    
    
    @Override
    public void trajeto(int quilometros) {
        
    }
    @Override
    public int ultimoTrajeto() {

        return 0;
        
    }
    @Override
    public int distanciaTotal() {
        return 0;
    }
    
    
}
