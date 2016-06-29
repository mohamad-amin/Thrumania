package thrumania.board.item.GameItems.LiveElementItems;

import java.util.ArrayList;

/**
 * Created by AMIR on 6/28/2016.
 */
public class Side {
    public static int numberOfPlayers=0;
    public static ArrayList<String> nameOfPlayers;
    private int numberOfPlayer;
    private String nameOfPlayer;

    public Side(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
        //TODO name of players
//        this.nameOfPlayer = nameOfPlayers.get(numberOfPlayer);
    }

    public void addplayer(String name){
        nameOfPlayers.add(name);
        numberOfPlayer++;
    }

    public static void setNumberOfPlayers(int numberOfPlayers) {
        Side.numberOfPlayers = numberOfPlayers;
    }

    public static void setNameOfPlayers(ArrayList<String> nameOfPlayers) {
        Side.nameOfPlayers = nameOfPlayers;
    }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public static ArrayList<String> getNameOfPlayers() {
        return nameOfPlayers;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public String getNameOfPlayer() {
        return nameOfPlayer;
    }

}
