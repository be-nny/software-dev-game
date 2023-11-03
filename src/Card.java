public class Card {
    private final int faceValue;

    /**
     * Creates a card object with a given face value
     * @param faceValue value displayed on the front of the card
     * */
    public Card(int faceValue){
        this.faceValue = faceValue;
    }
    /**
     * @return face value of the card
     * */
    public int getFaceValue(){
        return this.faceValue;
    }
}
