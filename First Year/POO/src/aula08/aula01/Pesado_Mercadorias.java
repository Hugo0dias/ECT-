package aula08.aula01;

public class Pesado_Mercadorias extends Conjunto_veiculos{

    int Num_Quadro;
    int peso;
    int cargaMax;
    
    public Pesado_Mercadorias(String Matricula, String marca, String modelo, int Potencia, int Num_Quadro, int peso, int cargaMax) {
        super(Matricula, marca, modelo, Potencia);
        this.Num_Quadro = Num_Quadro;
        this.peso = peso;
        this.cargaMax = cargaMax;
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

    public int getCargaMax() {
        return this.cargaMax;
    }

    public void setCargaMax(int cargaMax) {
        this.cargaMax = cargaMax;
    }


    @Override
    public String toString() {
        return "{" +
            " Num_Quadro='" + getNum_Quadro() + "'" +
            ", peso='" + getPeso() + "'" +
            ", cargaMax='" + getCargaMax() + "'" +
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
