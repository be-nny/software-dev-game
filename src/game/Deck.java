package game;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Deck {
    private volatile ArrayList<Card> deck = new ArrayList<>();

    /**
     * When created, the deck object needs to be filled with a {@link Pack game.Pack} object.
     * */
    public Deck(){}

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
     * @throws InterruptedException if the {@link ReentrantLock ReentrantLock} can't be obtained for the deck
     * @implNote This is thread safe
     * */
    public Card draw() throws InterruptedException {
        Card card;
        synchronized (this.deck){
            card = this.deck.get(this.deck.size()-1);
            this.deck.notifyAll();
        }
        return card;
    }

    /**
     * Adds a card to the bottom of the deck.
     * @throws InterruptedException if the {@link ReentrantLock ReentrantLock} can't be obtained for the deck
     * @implNote This is thread safe
     * */
    public void discard(Card card) throws InterruptedException {
        synchronized (this.deck){
            this.deck.add(0, card);
            this.deck.notifyAll();
        }
    }
}
