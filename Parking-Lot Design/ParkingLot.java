import java.util.*;

public class ParkingLot {
    private List<ParkingFloor> floors;
    public ParkingLot(List<ParkingFloor> floors) {
        this.floors = floors;
    }

    public Ticket generateTicket(Vehicle vehicle, long inTime, SlotType size) {

        for (ParkingFloor floor : floors) {
            ParkingSlot slot = floor.getAvailableSlot(size);

            if (slot != null) {
                slot.assignVehicle();

                return new Ticket(
                        UUID.randomUUID().toString(),
                        vehicle,
                        slot,
                        inTime
                );
            }
        }

        System.out.println("Parking Full!");
        return null;
    }

    public Bill generateBill(Ticket ticket, long outTime, double rate) {

        ticket.getSlot().removeVehicle();

        return new Bill(ticket, outTime, rate);
    }
}
