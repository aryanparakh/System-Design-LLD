import java.util.List;

public class Booking {

    int id;
    User user;
    Show show;
    List<Integer> seats;
    BookingStatus status;

    public Booking(int id, User user, Show show, List<Integer> seats) {
        this.id = id;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.status = BookingStatus.PENDING;
    }

    public void confirm() {
        status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        status = BookingStatus.CANCELLED;
    }
}
