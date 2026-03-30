import java.util.*;

public class Show {
    int id;
    Movie movie;
    Screen screen;

    Map<Integer, Boolean> seatStatus = new HashMap<>();

    public Show(int id, Movie movie, Screen screen) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;

        for (Seat seat : screen.seats) {
            seatStatus.put(seat.id, true); // true = available
        }
    }

    public boolean isAvailable(int seatId) {
        return seatStatus.getOrDefault(seatId, false);
    }

    public void bookSeat(int seatId) {
        seatStatus.put(seatId, false);
    }
}
