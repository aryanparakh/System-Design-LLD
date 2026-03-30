public class Main {

    public static void main(String[] args) {

        ElevatorSystem system = new ElevatorSystem(2);
        system.requestElevator(5, Direction.UP);


        Elevator e = system.getElevators().get(0);
        e.getPanel().pressFloorButton(8);
        e.move();
    }
}
