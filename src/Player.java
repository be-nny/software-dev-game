import java.util.ArrayList;

public class Player {

    private String name;
    private int drawDeckPointer;
    private int discardDeckPointer;
    private ArrayList<Card> hand = new ArrayList<>();

    /**
     * @param name name of the player
     * */
    public Player(String name){
        this.name = name;
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
}
