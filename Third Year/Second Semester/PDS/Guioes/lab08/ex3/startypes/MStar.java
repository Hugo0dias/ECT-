package startypes;
import java.awt.Color;

public class MStar extends StarType{
    public static StarType instance;

    public MStar() {
        super(1, Color.RED);
        this.description = "This is a long description of the M type star....";
        // This would store the values of physical properties for each type of star
        this.physicalProperties = new Float[10];
    }

    public static StarType getInstance(){
        if(instance == null){
            instance = new MStar();
        }
        return instance;
    }
}
