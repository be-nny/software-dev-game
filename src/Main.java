import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Player> players = new ArrayList<>();
    private static volatile ArrayList<Deck> decks = new ArrayList<>();
    private static Pack pack = new Pack();

    public static void main(String[] args) {
        System.out.println("Enter number of players...");
        Scanner myScanner = new Scanner(System.in);
        int numberPlayers = myScanner.nextInt();
        setupGame(numberPlayers);
    }

    private static void setupGame(int numPlayers){
        // setting up player and deck lists
        for (int i=0; i < numPlayers; i++){
            String name = "Player " + i;
            players.add(new Player(name));
            decks.add(new Deck());
        }

        // assigning discard and draw decks to players
        for (int i=0; i<decks.size(); i ++){;
            int discard;
            if (i+1 < decks.size()){
                discard = i+1;
            } else {
                discard = 0;
            }

            // assigning the pointers
            players.get(i).setDrawDeckPointer(i);
            players.get(i).setDiscardDeckPointer(discard);
        }
    }
}