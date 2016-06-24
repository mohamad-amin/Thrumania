package thrumania.managers;

import thrumania.board.item.GameItems.people.Human;

import java.util.ArrayList;

/**
 * Created by sina on 6/24/16.
 */
public class HumanManagers {
    // singletone
    static HumanManagers instance;
    private ArrayList<Human> humans = new ArrayList<>();

    public static HumanManagers getSharedInstance(){
         if( instance == null )
             instance = new HumanManagers();
        return  instance;


     }
}
