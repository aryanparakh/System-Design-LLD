public class Bill {
    private Ticket ticket;
    private long outTime;
    private double amount;

    public Bill(Ticket ticket, long outTime, double rate) {
        this.ticket= ticket;
        this.outTime = outTime;
         this.amount = calculateAmount(rate);
    }

    private double calculateAmount(double rate) {
        long duration = outTime - ticket.getInTime();
        long hours = duration / (1000 * 60 * 60);
        return hours * rate;
    }

    public double getAmount() {
        return amount;
    }
