package game;

import exceptions.InvalidPackException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    private static ArrayList<Player> players = new ArrayList<>();
    public static volatile ArrayList<Deck> decks = new ArrayList<>();
    private static Pack pack;
    private static final int handSize = 4;

    private static boolean isRunning = true;

    public static void main(String[] args) {
        System.out.println("Enter number of players...");
        Scanner myScanner = new Scanner(System.in);
        int numberPlayers = myScanner.nextInt();
        myScanner = new Scanner(System.in);

        System.out.println("Enter location of pack...");
        String packLocation = myScanner.nextLine();
        boolean isValidPack = setupPack(packLocation, numberPlayers);
      
        while(!isValidPack){
            System.out.println("\nEnter location of pack...");
            packLocation = myScanner.nextLine();
            isValidPack = setupPack(packLocation, numberPlayers);
        }

        setupGame(numberPlayers);
        Thread gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner scanner = new Scanner(System.in);
                System.out.println("-- Initial Hands --");
                for(Player player: players){
                    System.out.println(player.toString());
                }
                while (!isWin()){
                    ArrayList<Integer> actions = new ArrayList<>();

                    for(Player player: players){
                        System.out.println("\n" + player.toString());
                        System.out.println("Pick a card to discard (1 - 4)...");
                        int choice = scanner.nextInt() - 1;
                        while(choice < 0 || choice > 4){
                            System.out.println("Error :( Pick a card to discard (1 - 4)...");
                            choice = scanner.nextInt() - 1;
                        }
                        actions.add(choice);
                    }

                    for(Player player: players){
                        new Thread(() -> {
                            try {
                                player.turn(actions.get(0));
                                actions.remove(actions.get(0));

                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }).start();
                    }
                }
            }
        });
        gameThread.start();
    }

    /**
     * Sets up the hands for all players
     */
    private static void setupHands() {
        for (int i = 0; i < handSize; i++) {
            for (Player player : players) {
                player.addCard(pack.getTopCard());
            }
        }
    }

    /**
     * Sets up all decks that are used in the game
     */
    private static void setupDecks() {
        while (pack.getCards().size() > 0) {
            for (Deck deck : decks) {
                deck.addCard(pack.getTopCard());
            }
        }
    }

    /**
     * Sets up a pack from the pack text file
     *
     * @param packLocation  absolute path to the pack text file
     * @param numberPlayers the number of players in the game
     */
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

    /**
     * Sets up everything for the game to start.
     *
     * @param numPlayers the number of players in the game
     * */
    private static void setupGame(int numPlayers){
        for (int i=1; i < numPlayers+1; i++){
            String name = "Player " + i;
            players.add(new Player(name));
            decks.add(new Deck());
        }
        setupHands();
        setupDecks();

        // assigning discard and draw decks to players
        for (int i = 0; i < decks.size(); i++) {
            ;
            int discard;
            if (i + 1 < decks.size()) {
                discard = i + 1;
            } else {
                discard = 0;
            }

            // assigning the pointers
            players.get(i).setDrawDeckPointer(i);
            players.get(i).setDiscardDeckPointer(discard);
        }
    }

    private static boolean isWin() {
        for (Player player : players) {
            List<Integer> valueList = new ArrayList();
            for (Card card : player.getHand()) {
                valueList.add(card.getFaceValue());
            }
            if (valueList.stream().allMatch(valueList.get(0)::equals)) {
                return true;
            }
        }
        return false;
    }
}