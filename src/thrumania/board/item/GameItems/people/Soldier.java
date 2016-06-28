package thrumania.board.item.GameItems.people;

import thrumania.board.item.MapItems.HighLand;
import thrumania.board.item.MapItems.LowLand;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.PlayPanel;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

import java.util.Stack;

/**
 * Created by sina on 6/24/16.
 */
public class Soldier extends  Human implements Runnable {
    private PlayPanel playPanel;
    private Map map;
    private Stack<Coordinate> paths;
    private MapProcessor mapProcessor;
    private boolean isMoving;


    public Soldier(PlayPanel playPanel, Map map, int x, int y) {
        super.health = 1000;
        super.damageUnit = 70;
        super.visibilityUnit = 25;
        super.foodReq = 2000;
        super.ironReq = 500;
        super.goldReq = 250;
        super.woodReq = 600;
        super.speadOfConsumingFood = 2;
        super.isAlive = true;
        super.isSelectedByPlayer = false;
        // TODO:
        super.xCord =x;
        super.yCord = y;
        super.xEnd = xCord;
        super.yEnd = yCord;
        super.coordinate = IntegerUtils.getCoordinateWithXAndY(xCord , yCord);
        super.endCord = IntegerUtils.getCoordinateWithXAndY(xEnd , yEnd);

        this.playPanel = playPanel;
        this.map = map;
        this.mapProcessor =  new MapProcessor(map.getCells());
        // TODO : initializing its coordinate
        this.isMoving = false;


    }

    @Override
    protected void move(Coordinate end) {

        isMoving = true;
        while (coordinate.getRow() != end.getRow() || coordinate.getColumn() != end.getColumn()) {
            if (coordinate.getColumn() < end.getColumn())
                this.xCord ++;
            else if (coordinate.getColumn() > end.getColumn())
                this.xCord--;
            if (coordinate.getRow() > end.getRow())
                this.yCord--;
            else if (coordinate.getRow() < end.getRow())
                this.yCord++;
            coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);
            try {
                Thread.sleep((long) (1000 / speedOfMoving));
            } catch (InterruptedException e) {
                e.printStackTrace();

            }


        }
        isMoving = false;


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
        while (!coordinate.equals(endCord) && !mapProcessor.getPath(coordinate, endCord, this).isEmpty() &&
                (this.coordinate.getRow() != endCord.getRow() || this.coordinate.getColumn() != endCord.getColumn()) &&
                !isMoving && !this.checkWheterTheGoalCellIsWaterOrNot(
                mapProcessor.getPath(coordinate, endCord, this).pop())) {
            paths = mapProcessor.getPath(coordinate, endCord, this);
            this.determiningSpeedOfMoving();
            if (paths.peek().equals(coordinate)) {
                paths.pop();
            }
            this.move(paths.pop());
        }
        while (! coordinate.equals(endCord) && !isMoving && this.xCord + Constants.CELL_SIZE / 2 != xEnd || this.yCord + Constants.CELL_SIZE / 2 != yEnd) {

            this.determiningSpeedOfMoving();
            if (this.xCord + Constants.CELL_SIZE / 2 > xEnd)
                xCord--;
            else if (this.xCord + Constants.CELL_SIZE / 2 < xEnd)
                xCord++;
            if (this.yCord + Constants.CELL_SIZE / 2 > yEnd)
                yCord--;
            else if (this.yCord + Constants.CELL_SIZE / 2 < yEnd)
                yCord++;

            try {
                Thread.sleep((long) (1000 / speedOfMoving));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);


// TODO
    }



    private  boolean checkWheterTheGoalCellIsWaterOrNot(Coordinate crd){
        return !(map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand);


    }
}

