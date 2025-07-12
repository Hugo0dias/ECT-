package startypes;
import java.awt.Color;

public class BStar extends StarType{
    public static StarType instance;
    
    public BStar() {
        super(5, new Color(230, 252, 252));
        this.description = "This is a long description of the B type star....";
        // This would store the values of physical properties for each type of star
        this.physicalProperties = new Float[10];
    }

    public static StarType getInstance(){
        if(instance == null){
            instance = new BStar();
        }
        return instance;
    }
}