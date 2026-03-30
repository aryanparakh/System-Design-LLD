public class Payment {

    int id;
    double amount;

    public Payment(int id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public boolean process() {
        // simulate success
        return true;
    }
}
