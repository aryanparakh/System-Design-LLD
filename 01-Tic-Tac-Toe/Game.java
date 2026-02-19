import java.util.Scanner;

public class Game {
    private Board board;
    private Player p1;
    private Player p2;
    private Player currentPlayer;

    public Game() {
        this.board = new Board(3);
        this.p1 = new Player("Aaru", Symbol.X);
        this.p2 = new Player("Friend", Symbol.O);
        this.currentPlayer = p1;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            board.display();
            System.out.println(currentPlayer.getName() + "'s turn. Enter row and col:");
            int r = sc.nextInt();
            int c = sc.nextInt();

            if (board.isCellEmpty(r, c)) {
                board.applyMove(new Move(r, c, currentPlayer.getSymbol()));
                
                if (board.checkWinner(currentPlayer.getSymbol())) {
                    board.display();
                    System.out.println("Winner: " + currentPlayer.getName());
                    break;
                }
                if (board.isFull()) {
                    board.display();
                    System.out.println("It's a Draw!");
                    break;
                }
               
                currentPlayer = (currentPlayer == p1) ? p2 : p1;
            } else {
                System.out.println("Invalid Move");
            }
        }
        sc.close();
    }

    public static void main(String[] args) {
        new Game().start();
    }
}
