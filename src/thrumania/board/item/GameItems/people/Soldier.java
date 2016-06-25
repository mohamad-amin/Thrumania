package thrumania.board.item.GameItems.people;

import thrumania.board.item.MapItems.Map;
import thrumania.gui.PlayPanel;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;

/**
 * Created by sina on 6/24/16.
 */
public class Soldier extends  Human implements Runnable {
    private  PlayPanel playPanel;
    private Map map;


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


    }

    @Override
    protected void move(Coordinate end) {


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
        this.determiningSpeedOfMoving();

    }
}

