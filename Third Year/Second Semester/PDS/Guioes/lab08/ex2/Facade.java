
public class Facade extends Company{

    public Facade(){
        super();
    }
    
    @Override
    public void admitEmployee(Person person, double salary){
        super.admitEmployee(person, salary);
        SocialSecurity s = new SocialSecurity();
        s.regist(person);
        Insurance i = new Insurance();
        i.regist(person);
        Card c = new Card();
        c.createCard(person);
        Parking p = new Parking();
        p.allow(person);


    }
    
}
