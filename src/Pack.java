import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Pack {
    private String packLocation;
    private int numberPlayers;
    public Pack(String packLocation, int numberPlayers) throws FileNotFoundException {
        this.packLocation = packLocation;
        this.numberPlayers = numberPlayers;
        System.out.println(isValidPack());
    }
    private boolean isValidPack() throws FileNotFoundException {
        File myObj = new File(packLocation);
        Scanner myReader = new Scanner(myObj);
        int count = 0;
        while (myReader.hasNextLine()){
            count++;
            myReader.nextLine();
        }
        myReader.close();
        if (count == 8 * this.numberPlayers) {
            return true;
        }
        return false;
    }
}
