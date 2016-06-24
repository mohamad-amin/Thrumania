package thrumania.board.item.GameItems.people;

/**
 * Created by sina on 6/24/16.
 */
public class Soldier extends  Human implements Runnable {


    public Soldier() {
        super.health  = 1000;
        super.damageUnit = 70;
        super.visibilityUnit = 25;
        super.foodReq = 2000;
        super.ironReq = 500;
        super.goldReq = 250;
        super.woodReq = 600;
        super.speadOfConsumingFood =2;
        super.isAlive = true;
        // TODO:
//        super.xCord
//        super.yCord =


    }

    @Override
    protected void move() {
        super.determiningSpeedOfMoving();


    }

    @Override
    public void run() {

    }
}
