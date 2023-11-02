import exceptions.InvalidPackException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Player> players = new ArrayList<>();
    private static volatile ArrayList<Deck> decks = new ArrayList<>();
    private static Pack pack;
    private static final int handSize = 4;
    public static void main(String[] args) {
        System.out.println("Enter number of players...");
        Scanner myScanner = new Scanner(System.in);
        int numberPlayers = myScanner.nextInt();
        myScanner = new Scanner(System.in);

        System.out.println("Enter location of pack");
        String packLocation = myScanner.nextLine();
        boolean isValidPack = setupPack(packLocation, numberPlayers);
        while(!isValidPack){
            System.out.println("\nEnter location of pack");
            packLocation = myScanner.nextLine();
            isValidPack = setupPack(packLocation, numberPlayers);
        }

        setupGame(numberPlayers);
    }
    private static void setupHands(){
        for (int i = 0; i < handSize; i++) {
            for (Player player : players) {
                player.addCard(pack.getTopCard());
            }
        }
    }
    private static void setupDecks(){
        while (pack.getCards().size()>0){
            for (Deck deck: decks){
                deck.addCard(pack.getTopCard());
            }
        }
    }
    private static boolean setupPack(String packLocation, int numberPlayers) {
        try {
            pack = new Pack(packLocation, numberPlayers);
        } catch (FileNotFoundException | InvalidPackException e) {
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("Success! Pack is valid.");
        return true;
    }
    private static void setupGame(int numPlayers){
        for (int i=0; i < numPlayers; i++){
            String name = "Player " + i;
            players.add(new Player(name, pack));
            decks.add(new Deck());
        }
        setupHands();
        setupDecks();

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