package thrumania.board.item.GameItems.people;

import thrumania.utils.Constants;

/**
 * Created by sina on 6/24/16.
 */
public abstract class Human {
    // TODO : teams
    protected  int health;
    // each damage is in scale of 0.5 second
    protected  int damageUnit;
    //TODO : visibility
    // it is in scale of foot;
    protected  int visibilityUnit;
    // in scale of  foot in 1 second :
    protected float  speedOfMoving;
    // in scale of food in 1 second
    protected  int speadOfConsumingFood;
    // requirments:
    protected int foodReq ;
    protected int ironReq;
    protected  int woodReq;
    protected int goldReq;
    // TODO : add a class called : human position instead of code below
    protected int xCord;
    protected  int yCord;
    protected  boolean isAlive;
    protected abstract void  move();
    protected  void determiningSpeedOfMoving(){

        /* TODO:
            * spring -> 8
            * summer -> 7
            * autumn -> 5
            * winter -> 3
         */


    }

    public int getyCord() {
        return yCord;
    }

    public void setyCord(int yCord) {
        this.yCord = yCord;
    }

    public int getxCord() {

        return xCord;
    }

    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
