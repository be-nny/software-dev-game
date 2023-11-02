import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Deck {
    ReentrantLock lock = new ReentrantLock();
    private volatile ArrayList<Card> deck = new ArrayList<>();
    public Deck(){

    }
    public void addCard(Card card){
        this.deck.add(card);
    }

    public Card draw() throws InterruptedException {
        boolean isDeckAvailable = lock.tryLock(1, TimeUnit.SECONDS);
        Card card = null;
        if(isDeckAvailable){
            lock.lock();
            try{
                card = this.deck.get(this.deck.size()-1);
                this.deck.remove(card);
            } finally {
                lock.unlock();
            }
        }

        return card;
    }

    public void add(Card card) throws InterruptedException {
        boolean isDeckAvailable = lock.tryLock(1, TimeUnit.SECONDS);
        if(isDeckAvailable) {
            lock.lock();
            try{
                this.deck.add(0, card);
            } finally {
                lock.unlock();
            }
        }
    }
}
