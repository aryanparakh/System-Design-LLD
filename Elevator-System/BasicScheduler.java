import java.util.List;

public class BasicScheduler implements Scheduler {

    @Override
    public Elevator selectElevator(Request request, List<Elevator> elevators) {

        Elevator best = null;
        int minDist = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            int dist = Math.abs(e.getCurrentFloor() - request.floor);

            if (dist < minDist) {
                minDist = dist;
                best = e;
            }
        }

        return best;
    }
}
