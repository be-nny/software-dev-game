package game;

import exceptions.InvalidPackException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Pack {
    private String packLocation;
    private int numberPlayers;
    private Stack<Card> cards = new Stack<>();
    private File myFileObj;
    private Scanner myReader;

    /**
     * This creates a pack object filled with cards generated from a text file containing the face values. This should
     * contain 8*n values where n is the number of players.
     * @param packLocation the absolute path to the pack location
     * @param numberPlayers the number of players in the game
     * @throws FileNotFoundException when the path provided doesn't exist
     * @throws InvalidPackException when the pack provided is invalid
     * */
    public Pack(String packLocation, int numberPlayers) throws FileNotFoundException, InvalidPackException {
        this.packLocation = packLocation;
        this.numberPlayers = numberPlayers;
        if (!isValidPack()) {
            throw new InvalidPackException("Pack file doesn't have enough lines!");
        } else{
            this.create();
        }
    }

    /**
     * Method checks the pack text file if it's valid or not.
     * @return boolean depending on if the pack is valid
     * @throws FileNotFoundException when the path to the pack file cannot be found
     * */
    private boolean isValidPack() throws FileNotFoundException {
        this.myFileObj = new File(this.packLocation);
        this.myReader = new Scanner(this.myFileObj);
        int count = 0;
        String line;
        while (this.myReader.hasNextLine()){
            line = this.myReader.nextLine();
            if(!line.equals("")){
                count++;
            }
        }
        this.myReader.close();
        return count == 8 * this.numberPlayers;
    }

    /**
     * Once the pack is deemed valid, the pack is filled with cards generated from the pack text file.
     * @throws FileNotFoundException if the path to the pack text file cannot be found
     * */
    private void create() throws FileNotFoundException {
        this.myFileObj = new File(this.packLocation);
        this.myReader = new Scanner(this.myFileObj);

        while (this.myReader.hasNextLine()){
            int cardValue = this.myReader.nextInt();
            Card card = new Card(cardValue);
            this.cards.push(card);
        }
        this.myReader.close();
    }

    /**
     * @return {@link Stack<Card>} of the cards in the pack
     * */
    public Stack<Card> getCards(){
        return this.cards;
    }

    /**
     * @return the top card of the pack
     * */
    public Card getTopCard(){
        return this.cards.pop();
    }
}
