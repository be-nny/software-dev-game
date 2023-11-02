public class Player {

    private String name;
    private int drawDeckPointer;
    private int discardDeckPointer;

    public Player(String name){
        this.name = name;
    }

    public void setDrawDeckPointer(int pointer){
        this.drawDeckPointer = pointer;
    }

    public void setDiscardDeckPointer(int pointer){
        this.discardDeckPointer = pointer;
    }
}
