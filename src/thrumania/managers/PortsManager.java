package thrumania.managers;

import thrumania.board.item.GameItems.buildings.Port;
import thrumania.utils.Constants;

import java.util.ArrayList;

/**
 * Created by sina on 7/3/16.
 */
public class PortsManager {

    private ArrayList <Port> [] ports  ;
    static PortsManager portInstance;



public  static PortsManager getPortSharedInstance(){


    if( portInstance == null){

        portInstance = new PortsManager();
        portInstance.ports =  new ArrayList[Constants.NUMBER_OF_PLAYERS];


    }
    return  portInstance;


}

    public ArrayList<Port>[] getPorts() {
        return ports;
    }

    public void setPorts(ArrayList<Port>[] ports) {
        this.ports = ports;
    }
}
