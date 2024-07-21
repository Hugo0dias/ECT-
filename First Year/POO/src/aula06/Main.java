public class Main {

    public static void main(String[] args) {

        DateYMD d = new DateYMD(5, 10, 1988);
        Pessoa p = new Pessoa("Hugo Dias", 13213213, d);
        aluno al = new aluno("Andreia Melo", 9855678, new DateYMD(18, 7, 1990), new DateYMD(1, 9, 2018));
        Professor p1 = new Professor("Jorge Almeida", 3467225, new DateYMD(13, 3, 1967), "Associado", 4);

        Bolseiro bls = new Bolseiro("Igor Santos", 8976543, new DateYMD(11, 5, 1985), p1, 900);

        bls.setMontante(1050);
        System.out.println("Aluno: " + al.getNome());
        System.out.println(al);

        System.out.println("Bolseiro: " + bls.getNome() + ", NMec: " + bls.getNum_Mec() + ", Bolsa: "
                + bls.getMontante() + ", Orientador: " + bls.getOrientador());

        System.out.println(bls);
    }
}
