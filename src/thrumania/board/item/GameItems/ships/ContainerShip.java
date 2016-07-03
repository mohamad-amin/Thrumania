package thrumania.board.item.GameItems.ships;

import thrumania.board.item.GameItems.buildings.*;
import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.Cells.LowLand;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.PlayPanel;
import thrumania.managers.HumanManagers;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

import java.awt.*;

/**
 * Created by sina on 7/3/16.
 */
public class ContainerShip extends  Ships {
    private   int capaciyOfHavingHumanInside = 0;
    private final int  MAX_CAPACITY_OF_HAVING_PEOPLE_INSIDE = 10 ;
    private boolean isCapacityOfHavingPeopleInsideFull = false;
    private boolean canEmptyPoeple = false;
    private  boolean canTakePoeple = false;

    public ContainerShip(PlayPanel playPanel , Map map  , int xCord , int yCord , int playNumber) {

        super.playPanel = playPanel;
        super.map = map;
        super.mapProcessor  = new MapProcessor(map.getCells());
        super.xCord = xCord;
        super.yCord = yCord;
        super.coordinate = IntegerUtils.getCoordinateWithXAndY(xCord , yCord);
        super.foodReq = 0;
        super.woodReq = 400;
        super.ironReq= 1000 ;
        super.goldReq =500 ;
        super.unitOfConsumingFood = 1;
        this.canMove = true;
        super.isAlive = true;

    }
    private void consumingFood(){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    // TODO weShouldcheck port separately

    private boolean  checkWehterGoalCellIsPort(Coordinate crd){
        Cell cell = map.getCell(crd.getRow() , crd.getColumn());
        if( cell.getInsideElementsItems() instanceof  Port)
        {
            if( capaciyOfHavingHumanInside > 0)
            canEmptyPoeple =true;
            return true;

        }
        else return  false;
    }
    private void checkWetherCanEmptyShipOnGoalCell(Coordinate crd){

        Cell cell = map.getCell(crd.getRow() , crd.getColumn());
        if( cell instanceof LowLand ) {
            if (cell.getInsideElementsItems() == null || !(cell.getInsideElementsItems() instanceof Castle) ||
                    !(cell.getInsideElementsItems() instanceof Farm) || !(cell.getInsideElementsItems() instanceof MineQuarry)
                    || !(cell.getInsideElementsItems() instanceof WoodQuarry) ||
                    !(cell.getInsideElementsItems() instanceof Barrack)) {
               if(capaciyOfHavingHumanInside > 0)
                canEmptyPoeple = true;
            }else canEmptyPoeple = false;

        }else  canEmptyPoeple = false;


    }
    private  void checkWetherCapacityIsFull(){

        if( capaciyOfHavingHumanInside == MAX_CAPACITY_OF_HAVING_PEOPLE_INSIDE)
            isCapacityOfHavingPeopleInsideFull = true;
        else isCapacityOfHavingPeopleInsideFull = false;

    }

    private  void examiningPath(){


        switch (moveState){

            case STOP:{

                if ( ! pathOfCoordinates.isEmpty()){
                    moveState = StatesOfMoving.MOVE_BY_ORDER;

                }





                break;

            }
            case MOVE_BY_ORDER:
            {
                boolean isGoingToPickUp = false;

                if( pathOfCoordinates.equals(coordinate))
                    pathOfCoordinates.pop();

                if( capaciyOfHavingHumanInside != MAX_CAPACITY_OF_HAVING_PEOPLE_INSIDE && checkWetherGoalIsLand(pathOfCoordinates.peek())  && isAnyHumanTooPickUp(pathOfCoordinates.peek())){
                        isGoingToPickUp = true;
                    while (! pathOfCoordinates.isEmpty())
                        pathOfCoordinates.pop();
                    canTakePoeple =true;
                    canEmptyPoeple = false;
                }else if(! isGoingToPickUp &&  capaciyOfHavingHumanInside != 0 &&  checkWetherGoalIsLand(pathOfCoordinates.peek()))
                {
                    isGoingToPickUp = false;
                    while (! pathOfCoordinates.isEmpty())
                        pathOfCoordinates.pop();
                    canEmptyPoeple =true;
                    canTakePoeple = false;




                }








                break;
            }
            case COLLECTING_HUMAN:{







                break;
            }


            case  COLLECTING_HUMAN_IS_DONE:{








                break;
            }









        }








    }

    private boolean isAnyHumanTooPickUp(Coordinate crd){

        for ( int i =0 ; i < HumanManagers.getSharedInstance().getHumans()[playerNumber].size() ; i++){


            if( HumanManagers.getSharedInstance().getHumans()[playerNumber].get(i).getCoordinate().equals(crd))
                return  true;
        }
        return  false ;


    }
    @Override
    public void paintingOptions(Graphics g) {

    }

    @Override
    public void run() {

        while ( isAlive){
            consumingFood();
//            if(canMove)



        }

    }
}
