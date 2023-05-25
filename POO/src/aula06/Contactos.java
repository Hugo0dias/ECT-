
public class Contactos {

    private final int id;
    private Pessoa person;
    private String email;
    private int phone;
    private Contactos[] lista;

    public int getId() {
        return this.id;
    }

    public Pessoa getPerson() {
        return this.person;
    }

    public void setPerson(Pessoa person) {
        this.person = person;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return this.phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    private static int currentId = 1;

    public Contactos(Pessoa person, String email, int phone) {

        if ((email == null || email.isEmpty()) && (phone == 0)) {
            throw new IllegalArgumentException("Either email or phone must be provided");
        }
        this.id = Contactos.currentId++;
        this.person = person;
        this.setEmail(email);
        this.setPhone(phone);
    }

    public void addContacto(Contactos contacto) {

        for (int i = 0; i <= lista.length; i++) {
            if (lista[i] == null) {
                lista[i] = contacto;
                break;
            }
        }
    }

}
