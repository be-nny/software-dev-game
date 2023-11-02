import java.util.ArrayList;

public class Deck {
    private volatile ArrayList<Card> deck = new ArrayList<>();
    public Deck(){

    }
    public void addCard(Card card){
        this.deck.add(card);
    }
}
