import java.util.*;

public class ElevatorSystem {

    private List<Elevator> elevators;
    private Scheduler scheduler;

    public ElevatorSystem(int numElevators) {

        elevators = new ArrayList<>();
        scheduler = new BasicScheduler();

        for (int i = 1; i <= numElevators; i++) {
            elevators.add(new Elevator(i));
        }
    }

    public void requestElevator(int floor, Direction direction) {

        Request request = new Request(floor, direction);

        Elevator elevator = scheduler.selectElevator(request, elevators);

        System.out.println("Assigned Elevator " + elevator);

        elevator.addRequest(floor);
        elevator.move();
    }

    public List<Elevator> getElevators() {
        return elevators;
    }
}
