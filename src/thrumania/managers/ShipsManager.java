package thrumania.managers;

import thrumania.board.item.GameItems.ships.Ships;
import thrumania.utils.Constants;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by sina on 7/3/16.
 */
public class ShipsManager {

    static ShipsManager shipInstance ;
    private ArrayList<Ships> [] ships ;
    static ThreadPoolExecutor shipThreadPoolExecuter ;

    public static  ShipsManager getShipInstance () {
        if ( shipInstance == null){

            shipInstance = new ShipsManager();
            shipInstance.ships = new ArrayList[Constants.NUMBER_OF_PLAYERS];

        }
        return  shipInstance;



    }

    public ArrayList<Ships>[] getShips() {
        return ships;
    }

    public void setShips(ArrayList<Ships>[] ships) {
        this.ships = ships;
    }
}
