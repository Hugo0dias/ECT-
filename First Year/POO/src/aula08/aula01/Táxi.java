package aula08.aula01;

public class Táxi extends Automóvel_ligeiro{
    
    int Numero_licensa;

    public int getNumero_licensa() {
        return this.Numero_licensa;
    }

    public void setNumero_licensa(int Numero_licensa) {
        this.Numero_licensa = Numero_licensa;
    }

    public Táxi(String Matricula, String marca, String modelo, int Potencia, int num_quadro, int capacidade_bagageira, int Numero_licensa){
        super(Matricula, marca, modelo, Potencia, num_quadro, capacidade_bagageira);
        this.Numero_licensa = Numero_licensa;
    }


    @Override
    public String toString() {
        return "{" +
            " Numero_licensa='" + getNumero_licensa() + "'" + 
            "}";
    }

}
