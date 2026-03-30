public class InternalPanel {

    private Elevator elevator;

    public InternalPanel(Elevator elevator) {
        this.elevator = elevator;
    }

    public void pressFloorButton(int floor) {
        elevator.addRequest(floor);
    }

    public void pressOpenDoor() {
        System.out.println("Door opened");
    }

    public void pressCloseDoor() {
        System.out.println("Door closed");
    }

    public void pressAlarm() {
        System.out.println("🚨 Alarm triggered!");
    }
}
