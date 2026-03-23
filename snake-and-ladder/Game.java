import java.util.*;

public class Game {

    private Board board;
    private Queue<Player> players;
    private Dice dice;
    private int winningPosition;

    public Game(int size, int numPlayers, String difficulty) {

        int count = difficulty.equalsIgnoreCase("hard") ? size : size / 2;

        this.board = new Board(size, count);
        this.dice = new NormalDice();
        this.players = new LinkedList<>();
        this.winningPosition = size * size;

        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("Player " + i));
        }
    }

    public void start() {

        while (players.size() > 1) {

            Player player = players.poll();

            int roll = dice.roll();
            System.out.println(player.name + " rolled: " + roll);

            int newPos = player.position + roll;

            if (newPos > winningPosition) {
                System.out.println(player.name + " stays at " + player.position);
            } else {
                newPos = board.applySnakesAndLadders(newPos);
                player.position = newPos;
                System.out.println(player.name + " moved to " + newPos);
            }

            if (player.position == winningPosition) {
                System.out.println(player.name + " wins!");
            } else {
                players.add(player);
            }
        }

        System.out.println("Game Over!");
    }
}
