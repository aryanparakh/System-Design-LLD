import java.util.*;

public class Board {

    int size;
    List<Snake> snakes;
    List<Ladder> ladders;

    public Board(int size, int count) {
        this.size = size;
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        generateSnakesAndLadders(count);
    }

    private void generateSnakesAndLadders(int count) {
        Random rand = new Random();
        int max = size * size;

        Set<Integer> used = new HashSet<>();

        while (snakes.size() < count) {
            int head = rand.nextInt(max - 1) + 2;
            int tail = rand.nextInt(head - 1) + 1;

            if (!used.contains(head) && !used.contains(tail)) {
                snakes.add(new Snake(head, tail));
                used.add(head);
                used.add(tail);
            }
        }

        while (ladders.size() < count) {
            int start = rand.nextInt(max - 1) + 1;
            int end = rand.nextInt(max - start) + start + 1;

            if (!used.contains(start) && !used.contains(end)) {
                ladders.add(new Ladder(start, end));
                used.add(start);
                used.add(end);
            }
        }
    }

    public int applySnakesAndLadders(int pos) {

        for (Snake s : snakes) {
            if (s.head == pos) {
                System.out.println("🐍 Snake! Down to " + s.tail);
                return s.tail;
            }
        }

        for (Ladder l : ladders) {
            if (l.start == pos) {
                System.out.println("🪜 Ladder! Up to " + l.end);
                return l.end;
            }
        }

        return pos;
    }
}
