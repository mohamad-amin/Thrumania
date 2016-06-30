package thrumania.board.item.GameItems.people;

import thrumania.board.item.MapItems.Cells.HighLand;
import thrumania.board.item.MapItems.Cells.LowLand;
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
public class Soldier extends Human {
    private PlayPanel playPanel;
    private Map map;
    private Dimension d = new Dimension(Constants.CELL_SIZE, Constants.CELL_SIZE);


    public Soldier(PlayPanel playPanel, Map map, int x, int y , int playerNumber) {

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
        super.xCord = x;
        super.yCord = y;
//        super.xEnd = xCord;
//        super.yEnd = yCord;
        super.coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);
//        super.endCord = IntegerUtils.getCoordinateWithXAndY(xEnd, yEnd);
        // masir :



        // other classes :
        this.playPanel = playPanel;
        this.map = map;
        this.mapProcessor = new MapProcessor(map.getCells());
        // TODO : initializing its coordinate
        // booleans
        this.isMoving = false;
        super.isAlive = true;
        super.isSelectedByPlayer = false;
        super.attackMoveState = false;

        super.playerNumber = playerNumber;

        this.setSize(d);



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
    protected Human seeAnyFoes() {
        return  null;
    }


    @Override
    public void run() {

        while ( isAlive) {
            examiningPath();

        }
    }

    private boolean checkWheterTheGoalCellIsWaterOrNot(Coordinate crd) {
        if (map.getCell(crd.getRow(), crd.getColumn()) instanceof LowLand || map.getCell(crd.getRow(), crd.getColumn()) instanceof HighLand) {
            return false;
        } else {
            return true;
        }



    }
    private void settingEachMove(Stack<Coordinate> path) {
        if (!path.isEmpty())
            if (path.peek().equals(coordinate))
                path.pop();
        while (!path.isEmpty()) {
            if( !checkWheterTheGoalCellIsWaterOrNot(path.peek()))
                move(path.pop());
            else {

                while(!path.isEmpty())
                    path.pop();
            }
        }
    }
    public void examiningPath(){
        for ( int i =0 ; i<distinations.size() ; i++){
            System.out.println("size is \t"+ distinations.size());
            if( ! isMoving()) {
                settingEachMove(mapProcessor.getPath(coordinate, distinations.get(i), this));
                distinations.remove(i);
                isMoving = false;
            }
        }

    }


    protected void move( Coordinate end) {
        int xEnd, yEnd;

        isMoving = true;
        this.xCord = coordinate.getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10;
        this.yCord = coordinate.getRow() * Constants.CELL_SIZE;
        xEnd = end.getColumn() * Constants.CELL_SIZE + Constants.CELL_SIZE / 10;
        yEnd = end.getRow() * Constants.CELL_SIZE;

        while (this.xCord != xEnd || this.yCord != yEnd) {
            this.determiningSpeedOfMoving();


            if (this.xCord < xEnd)
                xCord++;
            else if (this.xCord > xEnd)
                xCord--;
            if (this.yCord < yEnd)
                yCord++;
            else if (this.yCord > yEnd)
                yCord--;
            this.setLocation(xCord, yCord);
            coordinate = IntegerUtils.getCoordinateWithXAndY(xCord, yCord);

            try {
                Thread.sleep((long) (1000 / (speedOfMoving * 5)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }


}



