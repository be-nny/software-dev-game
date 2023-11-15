package game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Deck extends Writer {
    private volatile ArrayList<Card> deck = new ArrayList<>();
    private final String deckOutputFile;
    private final String name;

    /**
     * When created, the deck object needs to be filled with a {@link Pack game.Pack} object.
     * */
    public Deck(int number) {
        this.name = "deck" + number;
        this.deckOutputFile = this.name + "_output.txt";
    }

    /**
     * Will return the Deck text string
     * @return the output string for the current deck
     * */
    public String getDeckOutputFile(){
        return this.deckOutputFile;
    }
    /**
     * Will return the deck corresponding with the appropriate player
     * @return name attribute from the deck
     * */
    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        String deckStr = "";
        for(Card card: this.deck){
            deckStr += card.getFaceValue() + " ";
        }
        return deckStr;
    }
    /**
     * This appends a card to the deck. This doesn't add it to the bottom. This is used when filling the deck from a
     * {@link Pack game.Pack}.
     * @param card the card to get inserted into the deck
     * */
    public void addCard(Card card){
        this.deck.add(card);
    }

    /**
     * Pops the top card from the deck.
     * @return game.Card object drawn from the top of the deck
     * @implNote This is thread safe
     * */
    public Card draw() {
        Card card;
        synchronized (this){
            card = this.deck.get(this.deck.size()-1);
            this.deck.remove(card);
            this.notifyAll();
        }
        return card;
    }

    /**
     * Adds a card to the bottom of the deck.
     * @param card the card to get discarded to the discard deck
     * @implNote This is thread safe
     * */
    public void discard(Card card) {
        synchronized (this){
            this.deck.add(0, card);
            this.notifyAll();
        }
    }


    /**
     * Creates the output text file for this deck
     * @throws @IOException
     * @return null value
     * */
    @Override
    public String initialise() throws IOException {
        File file = new File(this.deckOutputFile);
        FileWriter writer = new FileWriter(file);

        writer.write("");
        writer.close();
        return null;
    }
}
