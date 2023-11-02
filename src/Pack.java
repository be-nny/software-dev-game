import exceptions.InvalidPackException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Pack {
    private String packLocation;
    private int numberPlayers;
    private Stack<Card> cards = new Stack<>();


    public Pack(String packLocation, int numberPlayers) throws FileNotFoundException, InvalidPackException {
        this.packLocation = packLocation;
        this.numberPlayers = numberPlayers;
        if (!isValidPack()) {
            throw new InvalidPackException("Pack file doesn't have enough lines!");
        } else{
            this.create();
        }
    }

    private boolean isValidPack() throws FileNotFoundException {
        File myObj = new File(this.packLocation);
        Scanner myReader = new Scanner(myObj);
        int count = 0;
        while (myReader.hasNextLine()){
            count++;
            myReader.nextLine();
        }
        myReader.close();
        return count == 8 * this.numberPlayers;
    }

    private void create() throws FileNotFoundException {
        File myObj = new File(this.packLocation);
        Scanner myReader = new Scanner(myObj);

        while (myReader.hasNextLine()){
            int cardValue = myReader.nextInt();
            Card card = new Card(cardValue);
            this.cards.push(card);
        }
        myReader.close();
    }
    public Stack<Card> getCards(){
        return this.cards;
    }
    public Card getTopCard(){
        return this.cards.pop();
    }
}
