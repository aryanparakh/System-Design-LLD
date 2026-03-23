public class ParkingSlot {
    private int slotId;
    private SlotType type;
    private SlotStatus status;

    public ParkingSlot(int slotId, SlotType type) {
        this.slotId = slotId;
        this.type = type;
        this.status = SlotStatus.AVAILABLE;
    }

    public boolean isAvailable() {
        return status == SlotStatus.AVAILABLE;
    }

    public SlotType getType() {
        return type;
    }

    public void assignVehicle() {
        status = SlotStatus.OCCUPIED;
    }

    public void removeVehicle() {
        status = SlotStatus.AVAILABLE;
    }

    public int getSlotId() {
        return slotId;
    }
}
