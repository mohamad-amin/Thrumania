package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
import thrumania.utils.Coordinate;

import java.awt.*;

/**
 * Created by AMIR on 7/1/2016.
 */
public class Port extends LiveElements {
    //TODO : @amirhosein :  requirments for building one
    //TODO : @amirhossein : building a ship
private Coordinate neighborsea;
    private  Coordinate portsCoordinate;
    public Port (Coordinate realPosition, Coordinate startingPoint, Coordinate neighbourSea, int sideNumber ) {
        this.side = new Side(sideNumber);
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;
        this.neighborsea= neighbourSea;
        this.health = new Health(3000,3000);

        String pictureName = "castle.png";
        springPictureName = pictureName;
        summerPictureName = pictureName;
        autumnPictureName = pictureName;
        winterPictureName = pictureName;

    }


    public Coordinate getPortsCoordinate() {
        return portsCoordinate;
    }

    public void setPortsCoordinate(Coordinate portsCoordinate) {
        this.portsCoordinate = portsCoordinate;
    }

    public Coordinate getNeighborsea() {
        return neighborsea;
    }

    public void setNeighborsea(Coordinate neighborsea) {
        this.neighborsea = neighborsea;
    }

    @Override
    public void paintingOptions(Graphics g) {
        //task : building ship(not available if no one is in the port)
        //worker inside
        //ships produced
        //capacity of food stored
        //side
        //health
    }
}
