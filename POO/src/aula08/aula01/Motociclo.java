package aula08.aula01;

public class Motociclo extends Conjunto_veiculos{

    String tipo;

    public Motociclo(String Matricula, String marca, String modelo, int Potencia, String tipo) {
        super(Matricula, marca, modelo, Potencia);
        this.tipo = tipo;
    }


    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    @Override
    public String toString() {
        return "{" +
            " tipo='" + getTipo() + "'" +
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
