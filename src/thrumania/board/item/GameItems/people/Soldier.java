package thrumania.board.item.GameItems.people;

import thrumania.board.item.MapItems.HighLand;
import thrumania.board.item.MapItems.LowLand;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.PlayPanel;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;

import java.util.Stack;

/**
 * Created by sina on 6/24/16.
 */
public class Soldier extends  Human implements Runnable {
    private  PlayPanel playPanel;
    private Map map;
    private Stack<Coordinate> paths;
    private MapProcessor mapProcessor ;
    private  boolean isMoving;


    public Soldier(PlayPanel playPanel , Map map , int x , int y) {
        super.health  = 1000;
        super.damageUnit = 70;
        super.visibilityUnit = 25;
        super.foodReq = 2000;
        super.ironReq = 500;
        super.goldReq = 250;
        super.woodReq = 600;
        super.speadOfConsumingFood =2;
        super.isAlive = true;
        super.isSelectedByPlayer = false;
        // TODO:
        super.xCord = x;
        super.yCord = y;
        this.playPanel = playPanel;
        this.map = map;
        // TODO : initializing its coordinate
        this.coordinate = new Coordinate(  ((int) Math.ceil((double) yCord / (double)Constants.CELL_SIZE)) ,(int)  Math.ceil((double) xCord /(double) Constants.CELL_SIZE));
        this.endCord =this.coordinate;
        this.isMoving = false;


    }

    @Override
    protected void move(Coordinate end) {

        isMoving = true;
        while ( coordinate.getRow() != end.getRow() ||  coordinate.getColumn() != end.getColumn() ) {
            if( coordinate.getColumn() <  end.getColumn())
                this.xCord += 1;
            else if ( coordinate.getColumn() > end.getColumn())
                this.xCord --;
            if( coordinate.getRow() > end.getRow())
                this.yCord  --;
            else if( coordinate.getRow() < end.getRow())
                this.yCord ++;
            coordinate = new Coordinate((int) Math.ceil((double) yCord / (double)Constants.CELL_SIZE), (int) Math.ceil((double) xCord / (double) Constants.CELL_SIZE));
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
        System.out.println(" error is here: \t" + mapProcessor);
        while( ! mapProcessor.getPath(coordinate , endCord).isEmpty() && (this.coordinate.getRow() != endCord.getRow() || this.coordinate.getColumn() != endCord.getColumn()) && !isMoving  && ! this.checkWheterTheGoalCellIsWaterOrNot(mapProcessor.getPath(coordinate ,endCord).pop())) {
            paths = mapProcessor.getPath(coordinate, endCord);
            this.determiningSpeedOfMoving();
            if( paths.peek().equals(coordinate) )
                paths.pop();


            this.move(paths.pop());
        }
        while ( this.xCord + Constants.CELL_SIZE / 2 != xEnd ||  this.yCord + Constants.CELL_SIZE / 2 != yEnd){

            this.determiningSpeedOfMoving();
            if(  this.xCord + Constants.CELL_SIZE / 2  > xEnd)
                xCord -- ;
            else if(  this.xCord + Constants.CELL_SIZE / 2 < xEnd)
                xCord  ++;
            if( this.yCord + Constants.CELL_SIZE / 2 > yEnd)
                yCord -- ;
            else if ( this.yCord + Constants.CELL_SIZE / 2 < yEnd)
                yCord ++ ;
            try {
                Thread.sleep((long) (1000 / speedOfMoving));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }

    private  boolean checkWheterTheGoalCellIsWaterOrNot(Coordinate crd){
        if( map.getCell(crd.getRow() , crd.getColumn()) instanceof LowLand ||  map.getCell(crd.getRow() , crd.getColumn()) instanceof HighLand)
            return  false;
        else return  true;


    }
}

