import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Deck> decks = new ArrayList<>();
    private static Pack pack = new Pack();

    public static void main(String[] args) {
        System.out.println("Enter number of players...");
        Scanner myScanner = new Scanner(System.in);
        int numberPlayers = myScanner.nextInt();
        setupGame(numberPlayers);
    }

    private static void setupGame(int numPlayers){
        for (int i=0; i < numPlayers; i++){
            String name = "Player " + i;
            players.add(new Player(name));
            decks.add(new Deck());
        }

        //
    }
}