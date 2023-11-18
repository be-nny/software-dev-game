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
            throw new InvalidPackException("game.Pack file doesn't have enough lines!");
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
        File myObj = new File(this.packLocation);
        Scanner myReader = new Scanner(myObj);
        int count = 0;
        String line;
        while (myReader.hasNextLine()){
            line = myReader.nextLine();
            if(!line.equals("")){
                count++;
            }
        }
        myReader.close();
        return count == 8 * this.numberPlayers;
    }

    /**
     * Once the pack is deemed valid, the pack is filled with cards generated from the pack text file.
     * @throws FileNotFoundException if the path to the pack text file cannot be found
     * */
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
