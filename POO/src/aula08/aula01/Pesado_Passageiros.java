package aula08.aula01;

public class Pesado_Passageiros extends Conjunto_veiculos{

    
    int Num_Quadro;
    int peso;
    int Num_Passageiros_max;

    public Pesado_Passageiros(String Matricula, String marca, String modelo, int Potencia, int Num_Quadro, int peso, int Num_Passageiros_max) {
        super(Matricula, marca, modelo, Potencia);
        this.Num_Quadro = Num_Quadro;
        this.peso = peso;
        this.Num_Passageiros_max = Num_Passageiros_max;
    }
    

    public int getNum_Quadro() {
        return this.Num_Quadro;
    }

    public void setNum_Quadro(int Num_Quadro) {
        this.Num_Quadro = Num_Quadro;
    }

    public int getPeso() {
        return this.peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getNum_Passageiros_max() {
        return this.Num_Passageiros_max;
    }

    public void setNum_Passageiros_max(int Num_Passageiros_max) {
        this.Num_Passageiros_max = Num_Passageiros_max;
    }


    @Override
    public String toString() {
        return "{" +
            " Num_Quadro='" + getNum_Quadro() + "'" +
            ", peso='" + getPeso() + "'" +
            ", Num_Passageiros_max='" + getNum_Passageiros_max() + "'" +
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
