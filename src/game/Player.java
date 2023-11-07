package game;

import game.Card;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static game.Main.decks;

public class Player {

    private String name;
    private int number;
    private int drawDeckPointer;
    private int discardDeckPointer;
    private ArrayList<Card> hand = new ArrayList<>();
    private String outputFilePath;

    /**
     * @param name name of the player
     * @param number players associated number
     * */
    public Player(String name, int number){
        this.name = name;
        this.number = number;
        this.outputFilePath = this.name + "_output.txt";
    }

    public void write(String line) throws IOException {
        File file = new File(this.outputFilePath);
        FileWriter writer = new FileWriter(file);
        writer.write("\n"+line);
        writer.close();
    }
    /**
     * Adds a card to the players hand
     * @param card the card to be added to the players hand
     * */
    public void addCard(Card card){
        this.hand.add(card);
    }

    /**
     * Sets an integer value pointer which corresponds to the deck the player draws to
     * @param pointer integer pointer to deck
     * */
    public void setDrawDeckPointer(int pointer){
        this.drawDeckPointer = pointer;
    }

    /**
     * Sets an integer value pointer which corresponds to the deck the player discards to
     * @param pointer integer point to deck
     * */
    public void setDiscardDeckPointer(int pointer){
        this.discardDeckPointer = pointer;
    }

    @Override
    public String toString(){
        String strHand = "";
        for (Card card: this.hand){
            strHand += card.getFaceValue() + " ";
        }
        return this.name + "'s hand: " + strHand;
    }

    public ArrayList<Card> getHand(){
        return this.hand;
    }

    public int getNumber(){
        return this.number;
    }

    public String getName(){
        return this.name;
    }

    public synchronized void turn(int discardChoice) throws InterruptedException {
        Card discardCard = this.hand.get(discardChoice);
        this.hand.remove(discardCard);
        decks.get(discardDeckPointer).discard(discardCard);
        this.hand.add(decks.get(drawDeckPointer).draw());
        notifyAll();
    }
}
