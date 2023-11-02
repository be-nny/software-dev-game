import java.util.ArrayList;

public class Player {

    private String name;
    private int drawDeckPointer;
    private int discardDeckPointer;
    private ArrayList<Card> hand = new ArrayList<>();

    public Player(String name, Pack pack){
        this.name = name;
    }
    public void addCard(Card card){
        this.hand.add(card);
    }

    public void setDrawDeckPointer(int pointer){
        this.drawDeckPointer = pointer;
    }

    public void setDiscardDeckPointer(int pointer){
        this.discardDeckPointer = pointer;
    }
}
