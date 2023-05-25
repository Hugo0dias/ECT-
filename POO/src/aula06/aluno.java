public class aluno extends Pessoa {

    private int Num_Mec = 100;
    private DateYMD Data_insc;

    public aluno(String nome, int cc, DateYMD dataNasc, DateYMD Data_insc) {
        super(nome, cc, dataNasc);
        this.Data_insc = Data_insc;
    }

    @Override
    public String toString() {
        return "{" +
                " Num_Mec='" + getNum_Mec() + "'" +
                ", Data_insc='" + getData_insc() + "'" +
                "}";
    }

    public int getNum_Mec() {
        return this.Num_Mec;
    }

    public void setNum_Mec(int Num_Mec) {
        this.Num_Mec = Num_Mec;
    }

    public DateYMD getData_insc() {
        return this.Data_insc;
    }

    public void setData_insc(DateYMD Data_insc) {
        this.Data_insc = Data_insc;
    }

}