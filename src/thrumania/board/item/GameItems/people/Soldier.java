package thrumania.board.item.GameItems.people;

import thrumania.board.item.MapItems.HighLand;
import thrumania.board.item.MapItems.LowLand;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.PlayPanel;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

import java.awt.*;
import java.util.Stack;

/**
 * Created by sina on 6/24/16.
 */
public class Soldier extends  Human implements Runnable {
    private PlayPanel playPanel;
    private Map map;
    private Dimension d = new Dimension(Constants.CELL_SIZE , Constants.CELL_SIZE);


    public Soldier(PlayPanel playPanel, Map map, int x, int y) {

        // moshakhasat ;
        super.health = 1000;
        super.damageUnit = 70;
        super.visibilityUnit = 25;
        super.foodReq = 2000;
        super.ironReq = 500;
        super.goldReq = 250;
        super.woodReq = 600;
        super.speadOfConsumingFood = 2;

        // aks :
        // TODO : changing picure of shape
        super.picutreName = "manStanding.png";
        // TODO:

        // mokhtasat
        super.xCord =x;
        super.yCord = y;
        super.xEnd = xCord;
        super.yEnd = yCord;
        super.coordinate = IntegerUtils.getCoordinateWithXAndY(xCord , yCord);
        super.endCord = IntegerUtils.getCoordinateWithXAndY(xEnd , yEnd);
        // masir :

        super.paths = new Stack<>();



        // other classes :
        super.paths = new Stack<>();
        this.playPanel = playPanel;
        this.map = map;
        this.mapProcessor = new MapProcessor(map.getCells());
        // TODO : initializing its coordinate
        // booleans
        this.isMoving = false;
        super.isAlive = true;
        super.isSelectedByPlayer = false;

        this.setSize(d);
        this.setLocation(xCord , yCord);


    }

    @Override
    protected void move(Coordinate end) {
        this.endCord = end;
        this.xEnd = IntegerUtils.getXAndYWithCoordinate(end)[0];
        this.yEnd = IntegerUtils.getXAndYWithCoordinate(end)[1];

        isMoving = true;
        while (coordinate.getRow() != end.getRow() || coordinate.getColumn() != end.getColumn() && !movingShouldBeStopped) {
            if (!this.checkWheterTheGoalCellIsWaterOrNot(end)) {
                if (coordinate.getColumn() < end.getColumn())
                    this.xCord += 1;
                else if (coordinate.getColumn() > end.getColumn())
                    this.xCord--;
                if (coordinate.getRow() > end.getRow())
                    this.yCord--;
                else if (coordinate.getRow() < end.getRow())
                    this.yCord++;
                this.setLocation(xCord , yCord);
                coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);
                try {
                    Thread.sleep((long) (1000 / speedOfMoving));
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }


            } else {
                System.out.println("we are here and player should stop walking");
                movingShouldBeStopped = true;
                return;
            }
        }
        if (!this.paths.isEmpty())
            this.move(this.paths.pop());


    }

    @Override
    protected void determiningSpeedOfMoving() {
        if (playPanel.getSeason() == Constants.Seasons.SPRING)
            this.speedOfMoving = 8;
        else if (this.playPanel.getSeason() == Constants.Seasons.SUMMER)
            this.speedOfMoving = 7;
        else if (playPanel.getSeason() == Constants.Seasons.AUTMN)
            this.speedOfMoving = 5;
        else if (playPanel.getSeason() == Constants.Seasons.WINTER)
            this.speedOfMoving = 3;

    }


    @Override
    public void run() {
        if ( ! paths.isEmpty() && paths.peek().equals(coordinate))
            paths.pop();
        while (!paths.isEmpty()) {

            if (!this.checkWheterTheGoalCellIsWaterOrNot(paths.peek()) && !movingShouldBeStopped) {

                this.determiningSpeedOfMoving();

                if (paths.peek().equals(coordinate))
                    paths.pop();

                if (!paths.isEmpty())
                    this.move(paths.pop());
            } else break;
            if (!movingShouldBeStopped)
                while (this.xCord != xEnd || this.yCord != yEnd) {

                    this.determiningSpeedOfMoving();
                    if (this.xCord > xEnd)
                        xCord--;
                    else if (this.xCord < xEnd)
                        xCord++;
                    if (this.yCord > yEnd)
                        yCord--;
                    else if (this.yCord < yEnd)
                        yCord++;

                    try {
                        Thread.sleep((long) (1000 / speedOfMoving));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    this.setLocation(xCord , yCord);
                }

            coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);

        }
        isMoving = false;




// TODO
    }



    private  boolean checkWheterTheGoalCellIsWaterOrNot(Coordinate crd){
        return !(map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand);


    }
}

