import java.util.*;

public class Main {

    public static void main(String[] args) {

        // Seats
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            seats.add(new Seat(i, SeatType.REGULAR));
        }

        // Screen
        Screen screen = new Screen(1, seats);

        // Movie
        Movie movie = new Movie(1, "Avengers");

        // Show
        Show show = new Show(1, movie, screen);

        // User
        User user = new User(1, "Aryan");

        // Select seats
        List<Integer> selectedSeats = Arrays.asList(2, 3);

        // Check availability
        for (int seatId : selectedSeats) {
            if (!show.isAvailable(seatId)) {
                System.out.println("Seat not available!");
                return;
            }
        }

        // Booking
        Booking booking = new Booking(1, user, show, selectedSeats);

        // Payment
        Payment payment = new Payment(1, 200);

        if (payment.process()) {
            for (int seatId : selectedSeats) {
                show.bookSeat(seatId);
            }
            booking.confirm();
            System.out.println("Booking Confirmed!");
        } else {
            booking.cancel();
            System.out.println("Payment Failed!");
        }
    }
}
