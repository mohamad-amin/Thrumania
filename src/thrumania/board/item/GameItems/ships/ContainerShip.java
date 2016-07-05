package thrumania.board.item.GameItems.ships;

import thrumania.board.item.GameItems.buildings.*;
import thrumania.board.item.GameItems.people.Human;
import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.Cells.LowLand;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.PlayPanel;
import thrumania.managers.HumanManagers;
import thrumania.messages.EmptyingHuman;
import thrumania.messages.PickingHumanUp;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by sina on 7/3/16.
 */
public class ContainerShip extends  Ships {
    private   int capaciyOfHavingHumanInside = 0;
    private final int  MAX_CAPACITY_OF_HAVING_PEOPLE_INSIDE = 10 ;
    private boolean isCapacityOfHavingPeopleInsideFull = false;
    private boolean canEmptyPoeple = false;
    private  boolean canTakePoeple = false;
    private ArrayList<Human> indsideHumans  = new ArrayList<>();
    private Coordinate coordinateWhereHumanIsSettingToMap;
    private Human humanWhomeIsGoingToTheShip;
    private Dimension d = new Dimension(Constants.CELL_SIZE  + 20   , Constants.CELL_SIZE  + 20 );


    public ContainerShip(PlayPanel playPanel , Map map  , int xCord , int yCord , int playNumber) {
        System.out.println("constructed is called");
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
        this.playerNumber =playNumber;
        this.setSize(d);
        this.moveState = StatesOfMoving.STOP;

        pictureName = "F" + playerNumber % 4 + "" + 3  +".png";

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
               if(capaciyOfHavingHumanInside > 0) {
                   canEmptyPoeple = true;
                   coordinateWhereHumanIsSettingToMap = crd ;
               }
            }else canEmptyPoeple = false;

        }else  canEmptyPoeple = false;


    }
    private  void checkWetherCapacityIsFull(){

        if( capaciyOfHavingHumanInside == MAX_CAPACITY_OF_HAVING_PEOPLE_INSIDE)
            isCapacityOfHavingPeopleInsideFull = true;
        else isCapacityOfHavingPeopleInsideFull = false;

    }

    private  void examiningPath(){

//        System.out.println("here1234");
        switch (moveState){

            case STOP:{
                System.out.println(" capacity is \t" + this.capaciyOfHavingHumanInside);

                if ( ! pathOfCoordinates.isEmpty()){
                    moveState = StatesOfMoving.MOVE_BY_ORDER;
                }




                break;

            }
            case MOVE_BY_ORDER:
            {
                 boolean isGoingToPickUp = false;

                if( ! pathOfCoordinates.isEmpty()) {
                    if (pathOfCoordinates.equals(coordinate))
                        pathOfCoordinates.pop();
                        checkWetherCanEmptyShipOnGoalCell(pathOfCoordinates.peek());
                        checkWetherCapacityIsFull();
                    if( pathOfCoordinates.size() >1 && checkWetherGoalIsLand(pathOfCoordinates.peek())){
                        while ( ! pathOfCoordinates.isEmpty())
                            pathOfCoordinates.pop();

                    }
                 else   if (pathOfCoordinates.size() == 1 && ! isCapacityOfHavingPeopleInsideFull && checkWetherGoalIsLand(pathOfCoordinates.peek()) && isAnyHumanTooPickUp(pathOfCoordinates.peek())) {

                        isGoingToPickUp = true;
                        while (!pathOfCoordinates.isEmpty())
                            pathOfCoordinates.pop();
                        canTakePoeple = true;
                        canEmptyPoeple = false;
                        moveState = StatesOfMoving.COLLECTING_HUMAN;
                    } else if (pathOfCoordinates.size() == 1 && !isGoingToPickUp && capaciyOfHavingHumanInside != 0 && checkWetherGoalIsLand(pathOfCoordinates.peek()) && canEmptyPoeple) {
                        System.out.println("empy empty");
                        isGoingToPickUp = false;
                        while (!pathOfCoordinates.isEmpty())
                            pathOfCoordinates.pop();
                        canEmptyPoeple = true;
                        canTakePoeple = false;
                        moveState = StatesOfMoving.EMPTYING_HUMAN;
                    } else {
                        regularMove(pathOfCoordinates.pop());


                    }
                }else if ( pathOfCoordinates.isEmpty())
                    moveState =  StatesOfMoving.STOP;



                break;
            }
            case COLLECTING_HUMAN:{
                System.out.println("COllecting HUMAN HUMAN HUMAN");
                checkWetherCapacityIsFull();
                if ( ! pathOfCoordinates.isEmpty())
                    moveState = StatesOfMoving.MOVE_BY_ORDER;
                else if( pathOfCoordinates.isEmpty()){
                    if( canTakePoeple){
                        playPanel.dispatchEvent(new PickingHumanUp(this , this ,humanWhomeIsGoingToTheShip));
                        capaciyOfHavingHumanInside++;
                        canEmptyPoeple = true;
                        moveState = StatesOfMoving.STOP;

                    }else moveState = StatesOfMoving.STOP;


                }


                break;
            }


            case  COLLECTING_HUMAN_IS_DONE:{

                // TODO :

                moveState = StatesOfMoving.STOP;
                break;
            }

            case  EMPTYING_HUMAN:{
                System.out.println("emptying Human     ");

                if( ! pathOfCoordinates.isEmpty())
                    moveState = StatesOfMoving.MOVE_BY_ORDER;
                else if ( pathOfCoordinates.isEmpty()){
                    System.out.println("emptying human 1 ");
                    if( canEmptyPoeple){
                        System.out.println("emptying Human 2");
                        playPanel.dispatchEvent(new EmptyingHuman(playPanel , this , coordinateWhereHumanIsSettingToMap));
                        moveState = StatesOfMoving.EMPTYING_HUMAN_IS_DONE;
                        capaciyOfHavingHumanInside = 0;
                        canEmptyPoeple = false;
                        canTakePoeple = true;


                    }






                }







                break;
            }

            case  EMPTYING_HUMAN_IS_DONE:{
                // TODO :

                moveState = StatesOfMoving.STOP;





                break;
            }









        }








    }

    private boolean isAnyHumanTooPickUp(Coordinate crd){

        for ( int i =0 ; i < HumanManagers.getSharedInstance().getHumans()[playerNumber].size() ; i++){


            if( HumanManagers.getSharedInstance().getHumans()[playerNumber].get(i).getCoordinate().equals(crd)) {
                humanWhomeIsGoingToTheShip = HumanManagers.getSharedInstance().getHumans()[playerNumber].get(i) ;
                return true;
            }
        }
        return  false ;


    }
    @Override
    public void paintingOptions(Graphics g) {

    }

    @Override
    public void findingSelectedObject(int x, int y) {

    }

    @Override
    public void run() {

        while ( isAlive){
            consumingFood();
            deterimingCanMove();
            if(canMove)
                examiningPath();



        }

    }

    public boolean isCanTakePoeple() {
        return canTakePoeple;
    }

    public void setCanTakePoeple(boolean canTakePoeple) {
        this.canTakePoeple = canTakePoeple;
    }

    public ArrayList<Human> getIndsideHumans() {
        return indsideHumans;
    }

    public void setIndsideHumans(ArrayList<Human> indsideHumans) {
        this.indsideHumans = indsideHumans;
    }

    public boolean isCanEmptyPoeple() {
        return canEmptyPoeple;
    }

    public void setCanEmptyPoeple(boolean canEmptyPoeple) {
        this.canEmptyPoeple = canEmptyPoeple;
    }
}
