import java.util.List;

public class ParkingFloor {
    private int floorNumber;
    private List<ParkingSlot> slots;

    public ParkingFloor(int floorNumber, List<ParkingSlot> slots) {
        this.floorNumber = floorNumber;
        this.slots = slots;
    }

    public ParkingSlot getAvailableSlot(SlotType type) {
        for (ParkingSlot slot : slots) {
            if (slot.getType() == type && slot.isAvailable()) {
                return slot;
            }
        }
        return null;
    }
}
