public class BankAccountProxy implements BankAccount{
    private BankAccount bank;

    public BankAccountProxy(BankAccount bank){
        this.bank = bank;
    }

    @Override
    public void deposit(double amount){
        bank.deposit(amount);
    }

    @Override
    public boolean withdraw(double amount){
        if(User.OWNER == Company.user){
            return bank.withdraw(amount);
        } 

        return false;
    }

    @Override
    public double balance(){
        if(User.OWNER == Company.user){
            return bank.balance();
        }

        return -1;
    }
}
