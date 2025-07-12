import java.util.ArrayList;
import java.util.List;

public class Caixa implements Products {
    
    List<Products> Childrens;
    private final String name;
    private final double Weight;
    public static int identation;

    public Caixa(String name, double Weight) {
        this.name = name;
        this.Weight = Weight;
        this.Childrens = new ArrayList<>();
    } 

    public void add(Products Produto){
        Childrens.add(Produto);
    }

    public void remove(Products Produto){
        Childrens.remove(Produto);
    }

    @Override
    public double get_Weight(){
        double tot = Weight;
        for (Products p : Childrens){
            tot = p.get_Weight() + tot;
        }
        return tot;
    }

    /* 

    public static void printIdentation(){
        for (int i = 0; i < identationInc; i++){
            System.out.print("   ");
        }
    } */

    @Override
    public void draw() {
        for (int i = 0; i < identation; i++){
            System.out.print("   ");
        }
        System.out.println("* Caixa '" + name + "' [ Weight: " + Weight + "; Total: " + get_Weight() + " ]");
        identation ++;
        for (Products p : Childrens){
            p.draw();
        }
        identation --;

    }

}
