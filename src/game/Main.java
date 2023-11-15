package game;

import exceptions.InvalidPackException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static ArrayList<Player> players = new ArrayList<>();
    private static int numPlayers;
    private static ExecutorService executorService;
    public static volatile ArrayList<Deck> decks = new ArrayList<>();
    private static Pack pack;
    private static final int handSize = 4;

    private static Player winPlayer;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Enter number of players...");
        Scanner myScanner = new Scanner(System.in);
        numPlayers = myScanner.nextInt();
        myScanner = new Scanner(System.in);

        System.out.println("Enter location of pack...");
        String packLocation = myScanner.nextLine();
        boolean isValidPack = setupPack(packLocation, numPlayers);

        while(!isValidPack){
            System.out.println("\nEnter location of pack...");
            packLocation = myScanner.nextLine();
            isValidPack = setupPack(packLocation, numPlayers);
        }

        setupGame(numPlayers);
        try {
            game();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void game() throws InterruptedException, IOException {
        System.out.println("-- INITIAL HANDS --");
        for(Player player: players){
            System.out.println(player.initialise());
        }

        while (!isWin()){
            executorService = Executors.newFixedThreadPool(numPlayers);
            System.out.println("-- NEW ROUND --");
            for(Player player: players){
                executorService.execute(() -> {
                    System.out.println(player.getName() + " hand "+ player);
                    int number = pickCard(player);
                    try {
                        player.turn(number);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            executorService.shutdown();

            while (!executorService.isTerminated()){}
        }
        System.out.println("\n-- FINAL HANDS --");
        try{
            writeToFiles();
        } catch (IOException e){
            System.out.println("Error when writing to files" + e.getMessage());
        }
        System.out.println("\n" + winPlayer.getName() + " has won!");
        System.out.println(winPlayer.toString());
    }

    private static void writeToFiles() throws IOException {
        for(Player player: players){
            System.out.println(player.getName() + " hand "+ player);
            if (player == winPlayer){
                player.write("\n" + player.getName()+" wins", player.getOutputFilePath());
                player.write(("\n" + player.getName()+" exits"), player.getOutputFilePath());
                player.write("\n" + player.getName()+" final hand "+player, player.getOutputFilePath());
            }else{
                player.write("\n" + winPlayer.getName() + " has informed " + player.getName() + " that " + winPlayer.getName() + " has won", player.getOutputFilePath());
                player.write("\n" + player.getName() + " exits", player.getOutputFilePath());
                player.write("\n" + player.getName() + " final hand " + player, player.getOutputFilePath());
            }
        }

        for(Deck deck: decks){

        }
    }

    /**
     * Picks an index from the players hand to be discarded
     * @param player to have card picked
     * @return index of card from players hand to be discarded
     * */
    public static int pickCard(Player player){
        int number = player.getNumber();
        ArrayList<Integer> handIndex = new ArrayList<>();
        for(Card card: player.getHand()){
            if(card.getFaceValue() != number){
                handIndex.add(player.getHand().indexOf(card));
            }
        }
        Random random = new Random();
        return handIndex.get(random.nextInt(handIndex.size()));
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
            players.add(new Player(name, i));
            decks.add(new Deck(i));
        }
        setupHands();
        setupDecks();

        // assigning discard and draw decks to players
        for (int i = 0; i < decks.size(); i++) {
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
            List<Integer> valueList = new ArrayList<>();
            for (Card card : player.getHand()) {
                valueList.add(card.getFaceValue());
            }
            if (valueList.stream().allMatch(valueList.get(0)::equals)) {
                winPlayer = player;
                return true;
            }
        }
        return false;
    }
}