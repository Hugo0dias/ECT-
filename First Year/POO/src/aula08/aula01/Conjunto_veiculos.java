package aula08.aula01;
public abstract class Conjunto_veiculos  extends Empresa_aluguer implements KmPercorridosInterface{

    String Matricula;
    String marca;
    String modelo;
    int Potencia;

    public Conjunto_veiculos(String Matricula, String marca, String modelo, int Potencia) {
        this.Matricula = Matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.Potencia = Potencia;
    }


    public String getMatricula() {
        return this.Matricula;
    }

    public void setMatricula(String Matricula) {
        this.Matricula = Matricula;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getPotencia() {
        return this.Potencia;
    }

    public void setPotencia(int Potencia) {
        this.Potencia = Potencia;
    }


    @Override
    public String toString() {
        return "{" +
            " Matricula='" + getMatricula() + "'" +
            ", marca='" + getMarca() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", Potencia='" + getPotencia() + "'" +
            "}";
    }

    



    
    
}
