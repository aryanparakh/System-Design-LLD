import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<ParkingSlot> slots = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            slots.add(new ParkingSlot(i, SlotType.SMALL));
            slots.add(new ParkingSlot(i + 10, SlotType.MEDIUM));
            slots.add(new ParkingSlot(i + 20, SlotType.LARGE));
        }
        ParkingFloor floor1 = new ParkingFloor(1, slots);
        ParkingLot parkingLot = new ParkingLot(Arrays.asList(floor1));
        Vehicle vehicle = new Vehicle("KA01AB1234", VehicleType.CAR);

        Ticket ticket = parkingLot.generateTicket(
                vehicle,
                System.currentTimeMillis(),
                SlotType.MEDIUM
        );

        System.out.println("Ticket Generated: " + ticket.getTicketId());

        try { Thread.sleep(2000); } catch (Exception e) {}

        Bill bill = parkingLot.generateBill(
                ticket,
                System.currentTimeMillis(),
                20
        );

        System.out.println("Bill Amount: " + bill.getAmount());
    }
}
