import java.util.TreeSet;

public class Elevator {

    private int id;
    private int currentFloor;
    private Direction direction;
    private State state;

    private TreeSet<Integer> requests = new TreeSet<>();

    private InternalPanel panel;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.direction = Direction.UP;
        this.state = State.IDLE;
        this.panel = new InternalPanel(this);
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void addRequest(int floor) {
        requests.add(floor);
    }

    public void move() {

        while (!requests.isEmpty()) {

            Integer next;

            if (direction == Direction.UP) {
                next = requests.ceiling(currentFloor);
                if (next == null) {
                    direction = Direction.DOWN;
                    continue;
                }
            } else {
                next = requests.floor(currentFloor);
                if (next == null) {
                    direction = Direction.UP;
                    continue;
                }
            }

            requests.remove(next);
            currentFloor = next;

            System.out.println("Elevator " + id + " reached floor " + currentFloor);
        }

        state = State.IDLE;
    }

    public InternalPanel getPanel() {
        return panel;
    }
}
