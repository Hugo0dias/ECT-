import java.util.ArrayList;
import java.util.Iterator;

public class PlaneManager {

    private final ArrayList<Plane> Frota_avioes = new ArrayList<>();

    public void addPlane(Plane plane) {

        Frota_avioes.add(plane);

    }

    public void removePlane(String id) {

        Iterator<Plane> i = Frota_avioes.iterator();
        while (i.hasNext()) {
            Plane plane = i.next();
            if (plane.getid().equals(id)) {
                Frota_avioes.remove(plane);
                System.out.println("O aviao removido foi : " + toString());
                break;
            }
        }
        System.out.println("Aviao nao encontrado !!");

    }

    public void searchPlane(String id) {

        Iterator<Plane> i = Frota_avioes.iterator();

        while (i.hasNext()) {
            Plane plane = i.next();
            if (plane.getid().equals(id)) {
                System.out.println(plane);
            }

        }
        System.out.println("Nao encontrado!");

    }

    public void getCommercialPlanes() {

        ArrayList<Plane> comerciais = new ArrayList<>();

        for (Plane plane : Frota_avioes) {
            if (plane instanceof CommercialPlane) {
                comerciais.add(0, plane);
            }
        }
        System.out.println(comerciais);

    }

    public void getMilitaryPlanes() {

        ArrayList<Plane> militares = new ArrayList<>();

        for (Plane plane : Frota_avioes) {
            if (plane instanceof MilitaryPlane) {
                militares.add(0, plane);
            }
        }
        System.out.println(militares);

    }

    public void printAllPlanes() {

        System.out.println(Frota_avioes);

    }

    public void getFastestPlane() {

        Iterator<Plane> i = Frota_avioes.iterator();
        Plane fastplane = null;

        while (i.hasNext()) {

            Plane plane = i.next();
            if (fastplane == null || plane.getMaxSpeed() > fastplane.getMaxSpeed()) {
                fastplane = plane;
            }
        }

        System.out.println(fastplane);

    }

}
