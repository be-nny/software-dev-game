package game;

import game.Card;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static game.Main.decks;

public class Player extends Writer{

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

    public String initialise() throws IOException {
        this.write("", outputFilePath);

        String strHand = "";
        for (Card card: this.hand){
            strHand += card.getFaceValue() + " ";
        }
        String outputLine = "\n" + this.name + ": initial hand " + strHand;
        this.write(outputLine, this.outputFilePath);
        return outputLine;
    }

    @Override
    public String toString(){
        String strHand = "";
        for (Card card: this.hand){
            strHand += card.getFaceValue() + " ";
        }
        return strHand;
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

    public String getOutputFilePath(){
        return this.outputFilePath;
    }

    public synchronized void turn(int discardChoice) throws InterruptedException {
        Card discardCard = this.hand.get(discardChoice);
        this.hand.remove(discardCard);
        decks.get(this.discardDeckPointer).discard(discardCard);
        Card drawCard = decks.get(this.drawDeckPointer).draw();
        this.hand.add(drawCard);
        try {
            this.write("\n" + this.name+" draws a "+drawCard.getFaceValue()+" from deck "+(this.drawDeckPointer + 1), this.outputFilePath);
            this.write("\n" + this.name+" discards a "+discardCard.getFaceValue()+" from deck "+(this.discardDeckPointer + 1), this.outputFilePath);
            this.write ("\n" + this.name+" current hand is "+this, this.outputFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        notifyAll();
    }
}
