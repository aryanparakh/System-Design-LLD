public class Ticket {
    private String ticketId;
    private Vehicle vehicle;
    private ParkingSlot slot;
    private long inTime;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSlot slot, long inTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.slot = slot;
        this.inTime = inTime;
    }

    public long getInTime() {
        return inTime;
    }
    public ParkingSlot getSlot() {
        return slot;
    }
    public String getTicketId() {
        return ticketId;
    }
}
