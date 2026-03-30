import java.util.List;

public interface Scheduler {
    Elevator selectElevator(Request request, List<Elevator> elevators);
}
